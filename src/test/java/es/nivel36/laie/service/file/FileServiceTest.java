/*
* Laie
* Copyright (C) 2021  Abel Ferrer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.nivel36.laie.service.file;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest extends FileTest{

	@Nested
	class DownloadFile {

		@Test
		public void BadIdFileShouldThrowNullPointerException() {
			assertThrows(IllegalArgumentException.class, () -> {
				fileService.downloadFile(-1);
			});
		}

		@Test
		public void correctValueShouldReturnInputStream() throws Exception {
			final File file = mockFile();
			Mockito.when(fileJpaDao.find(1L)).thenReturn(file);
			final InputStream reader = new ByteArrayInputStream("test".getBytes());
			try (MockedConstruction<FileReader> mocked = Mockito.mockConstruction(FileReader.class, (mock, context) -> {
				when(mock.read()).thenReturn(reader);
			})) {
				final InputStream is = fileService.downloadFile(1);
				final InputStreamReader inputStreamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				final String text = new BufferedReader(inputStreamReader).lines().collect(Collectors.joining("\n"));
				Assertions.assertEquals(text, "test");
			}
		}
	}

	@Nested
	class FindFileById {

		@Test
		public void BadIdShouldThrowIllegalStateException() {
			assertThrows(IllegalArgumentException.class, () -> {
				fileService.findFileById(-1L);
			});
		}

		@Test
		public void ExistingIdShouldReturnAValue() {
			final File file = mockFile();
			Mockito.when(fileJpaDao.find(1L)).thenReturn(file);
			
			final FileDto fileDto = fileService.findFileById(1L);
			
			Assertions.assertNotNull(fileDto);
		}

		@Test
		public void NonExistingIdShouldReturnNull() {
			Mockito.when(fileJpaDao.find(1L)).thenThrow(NoResultException.class);
			
			final FileDto file = fileService.findFileById(1L);
			
			Assertions.assertNull(file);
		}
	}

	@Nested
	class RemoveFile {

		@Test
		public void BadIdShouldThrowIllegalStateException() {
			assertThrows(IllegalArgumentException.class, () -> {
				fileService.removeFile(-1L);
			});
		}

		@Test
		public void removeNonOrphanFileShouldntRemoveFile() {
			final File file = mockFile();
			Mockito.when(fileJpaDao.find(1L)).thenReturn(file);
			Mockito.when(fileJpaDao.isOrphan(1L)).thenReturn(false);
			try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
				
				fileService.removeFile(1L);
				
				utilities.verify(() -> Files.deleteIfExists(Mockito.any(Path.class)), Mockito.never());
			}
		}

		@Test
		public void removeOrphanFileShouldRemoveFile() {
			final File file = mockFile();
			Mockito.when(fileJpaDao.find(1L)).thenReturn(file);
			Mockito.when(fileJpaDao.isOrphan(1L)).thenReturn(true);
			try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
				utilities.when(() -> Files.deleteIfExists(Mockito.any(Path.class))).thenReturn(true);
				
				fileService.removeFile(1L);
				
				utilities.verify(() -> Files.deleteIfExists(Mockito.any(Path.class)), Mockito.atLeastOnce());
			}
		}
	}

	@Nested
	class UploadFile {

		@Test
		public void nullOutputStreamShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				fileService.uploadFile(null, "filename");
			});
		}

		@Test
		public void nullFilenameShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				OutputStream outputStream = Mockito.mock(OutputStream.class);
				fileService.uploadFile(outputStream, null);
			});
		}

		@Test
		public void uploadDuplicateFileShouldntUploadTheFile() {
			OutputStream outputStream = Mockito.mock(OutputStream.class);
			File file = mockFile();
			PhysicalFile physicalFile =  mockPhysicalFile();
			Mockito.when(fileJpaDao.findPhysicalFileByHash("HASH")).thenReturn(physicalFile);
			Mockito.when(fileJpaDao.save(Mockito.any(File.class))).thenReturn(file);
			try (MockedConstruction<Sha256DigestedFileWriter> mocked = Mockito
					.mockConstruction(Sha256DigestedFileWriter.class, (mock, context) -> {
						when(mock.write(outputStream)).thenReturn("HASH");
					})) {

				try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
					utilities.when(() -> Files.deleteIfExists(Mockito.any(Path.class))).thenReturn(true);
					
					FileDto fileDto = fileService.uploadFile(outputStream, "FileName");
					
					utilities.verify(() -> Files.deleteIfExists(Mockito.any(Path.class)), Mockito.atLeastOnce());
					Assertions.assertEquals(1L, fileDto.getId());
				}
			}
		}
		
		@Test
		public void uploadFileShouldUploadAndCreateRegistry() {
			OutputStream outputStream = Mockito.mock(OutputStream.class);
			File file = mockFile();
			Mockito.when(fileJpaDao.findPhysicalFileByHash("HASH")).thenReturn(null);
			Mockito.when(fileJpaDao.save(Mockito.any(File.class))).thenReturn(file);
			try (MockedConstruction<Sha256DigestedFileWriter> mocked = Mockito
					.mockConstruction(Sha256DigestedFileWriter.class, (mock, context) -> {
						when(mock.write(outputStream)).thenReturn("HASH");
					})) {

				try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
					utilities.when(() -> Files.deleteIfExists(Mockito.any(Path.class))).thenReturn(true);
					
					FileDto fileDto = fileService.uploadFile(outputStream, "FileName");
					
					utilities.verify(() -> Files.deleteIfExists(Mockito.any(Path.class)), Mockito.never());
					Assertions.assertEquals(1L, fileDto.getId());
				}
			}
		}
	}

	@Mock
	private FileJpaDao fileJpaDao;

	private FileService fileService;

	@BeforeEach
	public void setUp() {
		this.fileService = new FileService();
		this.fileService.setFileJpaDao(fileJpaDao);
		this.fileService.setFileDirectory("/tmp");
	}
}
