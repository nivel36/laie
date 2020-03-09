package ged.ejb.core.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Repository;
import ged.ejb.core.util.ConfigurationProperty;

@Stateless
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final static FileBucket PRIVATE_BUCKET = new PrivateFileBucket();

	private final static FileBucket PUBLIC_BUCKET = new PublicFileBucket();

	@Inject
	@Repository
	private FileJpaDao fileDao;

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	private PhysicalFile buildNewPhysicalFile(final FileBucket fileBucket, final String uuid, final Path absolutePath,
			final String hash) {
		final PhysicalFile newPhysicalFile = new PhysicalFile();
		newPhysicalFile.setUuid(uuid);
		newPhysicalFile.setContentHash(hash);
		newPhysicalFile.setAbsolutePath(absolutePath);
		newPhysicalFile.setCreated(LocalDateTime.now());
		final Path relativePath = getRelativePath(fileBucket, uuid);
		newPhysicalFile.setRelativePath(relativePath);
		return newPhysicalFile;
	}

	private Path getRelativePath(final FileBucket fileBucket, final String uuid) {
		return new PathBuilder().buildRelativePath(fileBucket, uuid);
	}

	private void deleteFileInFileSystem(final Path absolutePath) {
		try {
			Files.deleteIfExists(absolutePath);
		} catch (final IOException e) {
			throw new FileUploadException(e);
		}
	}

	public File findById(final long fileId) {
		if (fileId < 1) {
			logger.warn("Bad file id {}", fileId);
			throw new IllegalArgumentException("Bad file id: " + fileId);
		}
		logger.debug("Find file by id {}", fileId);
		return this.fileDao.find(fileId);
	}

	private Path getAbsolutePath(final FileBucket fileBucket, final String uuid) {
		return new PathBuilder().buildAbsolutePath(this.fileDirectory, fileBucket, uuid);
	}

	public InputStream getFile(final File file) {
		Objects.requireNonNull(file);
		try {
			final Path path = Paths.get(file.getPhysicalFile().getAbsolutePath());
			final InputStream is = Files.newInputStream(path);
			return new BufferedInputStream(is);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void removeFile(final File file) {
		Objects.requireNonNull(file);
		final PhysicalFile physicalFile = file.getPhysicalFile();
		final boolean isOrphan = this.fileDao.isOrphanPhysicalFile(physicalFile);
		if (isOrphan) {
			this.removePhysicalFile(physicalFile);
		}
		this.fileDao.delete(file);
	}

	private void removePhysicalFile(final PhysicalFile physicalFile) {
		try {
			final Path path = Paths.get(physicalFile.getAbsolutePath());
			Files.deleteIfExists(path);
			this.fileDao.deletePhysicalFile(physicalFile);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public File uploadFile(final InputStream inputStream, final boolean publicAccess, final String filename) {
		Objects.requireNonNull(inputStream);
		final FileBucket fileBucket = publicAccess ? PUBLIC_BUCKET : PRIVATE_BUCKET;
		final File file = new File(filename);
		file.setPublicAccess(publicAccess);
		final String uuid = UUID.randomUUID().toString();
		final Path absolutePath = getAbsolutePath(fileBucket, uuid);
		final String hash = this.uploadFileToFilesystem(absolutePath, inputStream);
		final PhysicalFile physicalFile = this.fileDao.findPhysicalFileByHash(hash);
		if (physicalFile != null) {
			file.setPhysicalFile(physicalFile);
			deleteFileInFileSystem(absolutePath);
		} else {
			final PhysicalFile newPhysicalFile = buildNewPhysicalFile(fileBucket, uuid, absolutePath, hash);
			file.setPhysicalFile(newPhysicalFile);
		}
		this.fileDao.save(file);
		return file;
	}

	private String uploadFileToFilesystem(final Path path, final InputStream inputStream) {
		return new Sha256DigestedFileWriter().write(path, inputStream);
	}
}
