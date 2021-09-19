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
package es.nivel36.laie.file;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import es.nivel36.laie.user.User;
import es.nivel36.laie.user.UserJpaDao;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest extends FileTest{

	@Nested
	class DownloadFile {

		@Test
		public void NullUidFileShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				fileService.downloadFile(null);
			});
		}

		@Test
		public void correctValueShouldReturnInputStream() throws Exception {
			final File file = mockFile();
			Mockito.when(fileJpaDao.findByUid("1")).thenReturn(file);
			final InputStream reader = new ByteArrayInputStream("test".getBytes());
			try (MockedConstruction<FileReader> mocked = Mockito.mockConstruction(FileReader.class, (mock, context) -> {
				when(mock.read()).thenReturn(reader);
			})) {
				final InputStream is = fileService.downloadFile("1");
				final InputStreamReader inputStreamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				final String text = new BufferedReader(inputStreamReader).lines().collect(Collectors.joining("\n"));
				Assertions.assertEquals(text, "test");
			}
		}
	}

	@Nested
	class FindFileByUid {

		@Test
		public void NullIdShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				fileService.findFileByUid(null);
			});
		}

		@Test
		public void ExistingIdShouldReturnAValue() {
			final File file = mockFile();
			Mockito.when(fileJpaDao.findByUid("1")).thenReturn(file);
			
			final FileDto fileDto = fileService.findFileByUid("1");
			
			Assertions.assertNotNull(fileDto);
		}

		@Test
		public void NonExistingIdShouldReturnNull() {
			Mockito.when(fileJpaDao.findByUid("1")).thenThrow(NoResultException.class);
			
			final FileDto file = fileService.findFileByUid("1");
			
			Assertions.assertNull(file);
		}
	}

	@Nested
	class RemoveFile {

		@Test
		public void BadIdShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				fileService.removeFile(null);
			});
		}

		@Test
		public void removeNonOrphanFileShouldntRemoveFile() {
			final File file = mockFile();
			Mockito.when(fileJpaDao.findByUid("1")).thenReturn(file);
			Mockito.when(fileJpaDao.isOrphan(1L)).thenReturn(false);
			try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
				
				fileService.removeFile("1");
				
				utilities.verify(() -> Files.deleteIfExists(Mockito.any(Path.class)), Mockito.never());
			}
		}

		@Test
		public void removeOrphanFileShouldRemoveFile() {
			final File file = mockFile();
			Mockito.when(fileJpaDao.findByUid("1")).thenReturn(file);
			Mockito.when(fileJpaDao.isOrphan(1L)).thenReturn(true);
			try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
				utilities.when(() -> Files.deleteIfExists(Mockito.any(Path.class))).thenReturn(true);
				
				fileService.removeFile("1");
				
				utilities.verify(() -> Files.deleteIfExists(Mockito.any(Path.class)), Mockito.atLeastOnce());
			}
		}
	}

	@Nested
	class UploadFile {

		@Test
		public void nullOutputStreamShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				fileService.uploadFile(null, "filename", null);
			});
		}

		@Test
		public void nullFilenameShouldThrowNullPointerException() {
			assertThrows(NullPointerException.class, () -> {
				InputStream outputStream = Mockito.mock(InputStream.class);
				fileService.uploadFile(outputStream, null, null);
			});
		}

		@Test
		public void uploadDuplicateFileShouldntUploadTheFile() {
			InputStream inputStream = Mockito.mock(InputStream.class);
			File file = mockFile();
			User user = new User();
			PhysicalFile physicalFile =  mockPhysicalFile();
			Mockito.when(fileJpaDao.findPhysicalFileByHash("HASH")).thenReturn(physicalFile);
			Mockito.when(fileJpaDao.insert(Mockito.any(File.class))).thenReturn(file);
			Mockito.when(userJpaDao.findUserByUid("uid")).thenReturn(user);
			try (MockedConstruction<Sha256DigestedFileWriter> mocked = Mockito
					.mockConstruction(Sha256DigestedFileWriter.class, (mock, context) -> {
						when(mock.write(inputStream)).thenReturn("HASH");
					})) {

				try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
					utilities.when(() -> Files.deleteIfExists(Mockito.any(Path.class))).thenReturn(true);
					
					FileDto fileDto = fileService.uploadFile(inputStream, "FileName", "uid");
					
					utilities.verify(() -> Files.deleteIfExists(Mockito.any(Path.class)), Mockito.atLeastOnce());
					Assertions.assertEquals("1", fileDto.getUid());
				}
			}
		}
		
		@Test
		public void uploadFileShouldUploadAndCreateRegistry() {
			InputStream inputStream = Mockito.mock(InputStream.class);
			File file = mockFile();
			User user = new User();
			Mockito.when(fileJpaDao.findPhysicalFileByHash("HASH")).thenReturn(null);
			Mockito.when(fileJpaDao.insert(Mockito.any(File.class))).thenReturn(file);
			Mockito.when(userJpaDao.findUserByUid("uid")).thenReturn(user);
			try (MockedConstruction<Sha256DigestedFileWriter> mocked = Mockito
					.mockConstruction(Sha256DigestedFileWriter.class, (mock, context) -> {
						when(mock.write(inputStream)).thenReturn("HASH");
					})) {

				try (MockedStatic<Files> utilities = Mockito.mockStatic(Files.class)) {
					utilities.when(() -> Files.deleteIfExists(Mockito.any(Path.class))).thenReturn(true);
					
					FileDto fileDto = fileService.uploadFile(inputStream, "FileName", "uid");
					
					utilities.verify(() -> Files.deleteIfExists(Mockito.any(Path.class)), Mockito.never());
					Assertions.assertEquals("1", fileDto.getUid());
				}
			}
		}
	}
	
	@Mock
	private UserJpaDao userJpaDao;

	@Mock
	private FileJpaDao fileJpaDao;

	private FileService fileService;

	@BeforeEach
	public void setUp() {
		this.fileService = new FileService();
		this.fileService.setFileJpaDao(fileJpaDao);
		this.fileService.setUserJpaDao(userJpaDao);
		this.fileService.setFileDirectory("/tmp");
	}
}
