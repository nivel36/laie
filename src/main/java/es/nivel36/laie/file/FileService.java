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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.service.Repository;

@Stateless
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Inject
	@ConfigProperty(name = "file.directory")
	private String fileDirectory;

	@Inject
	@Repository
	private FileJpaDao fileJpaDao;

	///////////////////////////////////////////////////////////////////////////
	// PUBLIC
	///////////////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Gets a file from the file system from its identifier
	 * </p>
	 *
	 * @param fileId <tt>long</tt> with the identifier of the file to download
	 * @return <tt>InputStream</tt> pointing the file
	 */
	public InputStream downloadFile(final long fileId) {
		if (fileId < 1) {
			logger.error("Bad file id {}", fileId);
			throw new IllegalArgumentException("Bad file id: " + fileId);
		}
		logger.debug("Downloading file {}", fileId);
		final File file = this.fileJpaDao.find(fileId);
		final Path path = file.getPath();
		return readFileFromFileSystem(path);
	}

	/**
	 * <p>
	 * Searches the database for a file by its id.
	 * </p>
	 *
	 * @param fileId <tt>long</tt> with the identifier of the file.
	 * @return <tt>FileDto</tt> if a file with such an id is found in the database,
	 *         <tt>null</tt> if it does not exist.
	 */
	public FileDto findFileById(final long fileId) {
		if (fileId < 1) {
			logger.error("Bad file id {}", fileId);
			throw new IllegalArgumentException("Bad file id: " + fileId);
		}
		logger.debug("Find file by its id {}", fileId);
		try {
			final File file = this.fileJpaDao.find(fileId);
			return new FileDtoMapper(file).map();
		} catch (final NoResultException e) {
			return null;
		}
	}

	/**
	 * <p>
	 * Delete a file.<br/>
	 * If the file is duplicated (it has been uploaded several times and there are
	 * multiple records in the database) the record in the database will be deleted
	 * but not the physical copy in the file system. If the file is not duplicated,
	 * the record in the database and the record in the file system will be deleted.
	 * </p>
	 *
	 * @param fileId <tt>long</tt> with the file identifier of the file to be
	 *               deleted.
	 */
	public void removeFile(final long fileId) {
		if (fileId < 1) {
			logger.error("Bad file id {}", fileId);
			throw new IllegalArgumentException("Bad file id: " + fileId);
		}
		logger.debug("Removing file {}", fileId);
		final File file = this.fileJpaDao.find(fileId);
		final PhysicalFile physicalFile = file.getPhysicalFile();
		final boolean isOrphan = this.fileJpaDao.isOrphan(physicalFile.getId());
		this.fileJpaDao.remove(file);
		if (isOrphan) {
			logger.trace("File is orphan");
			final Path path = file.getPath();
			final boolean deleted = this.removeFileFromFilesystem(path);
			if (!deleted) {
				logger.error("File {} not found in the file system", fileId);
			}
		}
	}

	/**
	 * <p>
	 * Upload a file to the file system and create a record in the database. <br/>
	 * When the file is uploaded, a hash of the content is created to detect
	 * duplicate files. If the file already exists on the file system only one copy
	 * (the previous one) is left but a record is still created in the database.
	 * Thus it is possible for a single file to have multiple records.
	 * </p>
	 *
	 * @param outputStream <tt>OutputStream</tt> of the file to be uploaded. The
	 *                     stream must be closed after use.
	 * @param filename     <tt>String</tt> with the name of the file.
	 * @return <tt>FileDto</tt> with the file data.
	 */
	public FileDto uploadFile(final OutputStream outputStream, final String filename) {
		Objects.requireNonNull(outputStream);
		Objects.requireNonNull(filename);
		logger.debug("Uploading file {}", filename);
		final File file = new File(filename);
		final String uuid = UUID.randomUUID().toString();
		final Path absolutePath = this.buildPath(uuid);
		final String hash = this.uploadFileToFilesystem(absolutePath, outputStream);
		final PhysicalFile physicalFile = this.fileJpaDao.findPhysicalFileByHash(hash);
		if (physicalFile != null) {
			logger.debug("Duplicated file found");
			this.removeFileFromFilesystem(absolutePath);
		} else {
			final PhysicalFile newPhysicalFile = new PhysicalFile(uuid, absolutePath, hash);
			file.setPhysicalFile(newPhysicalFile);
		}
		final File savedFile = this.fileJpaDao.save(file);
		return new FileDtoMapper(savedFile).map();
	}

	///////////////////////////////////////////////////////////////////////////
	// PRIVATE
	///////////////////////////////////////////////////////////////////////////

	private Path buildPath(final String uuid) {
		return Paths.get(this.fileDirectory, uuid.substring(0, 1), uuid.substring(1, 2), uuid);
	}

	private InputStream readFileFromFileSystem(final Path path) {
		try {
			final FileReader fileReader = new FileReader(path);
			return fileReader.read();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private boolean removeFileFromFilesystem(final Path path) {
		try {
			return Files.deleteIfExists(path);
		} catch (final IOException e) {
			return false;
		}
	}

	private String uploadFileToFilesystem(final Path path, final OutputStream outputStream) {
		try {
			final DigestedFileWriter fileWriter = new Sha256DigestedFileWriter(path);
			return fileWriter.write(outputStream);
		} catch (IOException | NoSuchAlgorithmException e) {
			throw new FileUploadException(e);
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// SETTERS
	///////////////////////////////////////////////////////////////////////////

	public void setFileDirectory(final String fileDirectory) {
		Objects.requireNonNull(fileDirectory);
		this.fileDirectory = fileDirectory;
	}

	public void setFileJpaDao(final FileJpaDao fileJpaDao) {
		Objects.requireNonNull(fileJpaDao);
		this.fileJpaDao = fileJpaDao;
	}
}
