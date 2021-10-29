package es.nivel36.laie.ejb.core.file;

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

import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.util.ConfigurationProperty;

@Stateless
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final FileBucket PRIVATE_BUCKET = new PrivateFileBucket();

	private static final FileBucket PUBLIC_BUCKET = new PublicFileBucket();

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

	public FileDto findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find file by uid {}", uid);
		final File file = this.fileDao.findFileByUid(uid);
		final FileMapper fileMapper = new FileMapper();
		return fileMapper.map(file);
	}

	private Path getAbsolutePath(final FileBucket fileBucket, final String uuid) {
		return new PathBuilder().buildAbsolutePath(this.fileDirectory, fileBucket, uuid);
	}

	public InputStream getFile(final String uid) {
		Objects.requireNonNull(uid);
		try {
			final File file = this.fileDao.findFileByUid(uid);
			final Path path = Paths.get(file.getPhysicalFile().getAbsolutePath());
			return new BufferedInputStream(Files.newInputStream(path));
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void removeFile(final String uid) {
		Objects.requireNonNull(uid);
		final File file = this.fileDao.findFileByUid(uid);
		final PhysicalFile physicalFile = file.getPhysicalFile();
		final boolean isOrphan = this.fileDao.isOrphanPhysicalFile(physicalFile);
		this.fileDao.delete(file);
		if (isOrphan) {
			this.removePhysicalFile(physicalFile);
		}
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

	public FileDto uploadTemporalFile(final InputStream inputStream) {
		Objects.requireNonNull(inputStream);
		final FileBucket fileBucket = PRIVATE_BUCKET;
		final String uuid = UUID.randomUUID().toString();
		final File file = new File("temporal." + uuid);
		file.setPublicAccess(false);
		final Path absolutePath = getAbsolutePath(fileBucket, uuid);
		final String hash = this.uploadFileToFilesystem(absolutePath, inputStream);
		final PhysicalFile newPhysicalFile = buildNewPhysicalFile(fileBucket, uuid, absolutePath, hash);
		file.setPhysicalFile(newPhysicalFile);
		final FileMapper fileMapper = new FileMapper();
		return fileMapper.map(file);
	}

	public FileDto uploadFile(final InputStream inputStream, final String filename, final boolean publicAccess) {
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
		this.fileDao.insert(file);
		return new FileMapper().map(file);
	}

	private String uploadFileToFilesystem(final Path path, final InputStream inputStream) {
		return new Sha256DigestedFileWriter().write(path, inputStream);
	}
}
