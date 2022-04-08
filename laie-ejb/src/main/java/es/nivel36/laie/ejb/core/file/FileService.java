package es.nivel36.laie.ejb.core.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.util.ConfigurationProperty;

@Stateless
public class FileService {

	private static final FileBucket TEMP_BUCKET = TemporalFileBucket.getInstance();

	private static final FileBucket PRIVATE_BUCKET = PrivateFileBucket.getInstance();

	private static final FileBucket PUBLIC_BUCKET = PublicFileBucket.getInstance();

	@Inject
	@Repository
	private FileJpaDao fileDao;

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	public InputStream downloadFile(final Long id) {
		Objects.requireNonNull(id);
		try {
			final File file = this.fileDao.find(File.class, id);
			final Path path = Paths.get(file.getPhysicalFile().getAbsolutePath());
			return new BufferedInputStream(Files.newInputStream(path));
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public InputStream downloadTemporalFile(final String path) {
		Objects.requireNonNull(path);
		try {
			final Path absolutePath = Paths.get(this.fileDirectory,path);
			return new BufferedInputStream(Files.newInputStream(absolutePath));
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public File uploadTemporalFile(final InputStream inputStream) {
		Objects.requireNonNull(inputStream);
		final File file = new File();
		file.setCreated(LocalDateTime.now());
		file.setPublicAccess(false);
		final PhysicalFile newPhysicalFile = uploadFileToBucket(TEMP_BUCKET, inputStream);
		file.setPhysicalFile(newPhysicalFile);
		file.setName(newPhysicalFile.getUuid());
		return file;
	}

	private PhysicalFile uploadFileToBucket(final FileBucket bucket, final InputStream inputStream) {
		final String uuid = UUID.randomUUID().toString();
		final Path relativePath = this.getRelativePath(bucket, uuid);
		final Path absolutePath = this.getAbsolutePath(relativePath);
		final String hash = this.uploadFileToFilesystem(absolutePath, inputStream);
		final PhysicalFile newPhysicalFile = new PhysicalFile();
		newPhysicalFile.setUuid(uuid);
		newPhysicalFile.setBucket(bucket.getName());
		newPhysicalFile.setContentHash(hash);
		newPhysicalFile.setAbsolutePath(absolutePath);
		newPhysicalFile.setCreated(LocalDateTime.now());
		newPhysicalFile.setRelativePath(relativePath);
		return newPhysicalFile;
	}

	private Path getRelativePath(final FileBucket fileBucket, final String uuid) {
		return new PathBuilder().buildRelativePath(fileBucket, uuid);
	}

	private Path getAbsolutePath(final Path relativePath) {
		return new PathBuilder().buildAbsolutePath(this.fileDirectory, relativePath);
	}

	private String uploadFileToFilesystem(final Path path, final InputStream inputStream) {
		return new Sha256DigestedFileWriter().write(path, inputStream);
	}

	public void removeFile(final File file) {
		Objects.requireNonNull(file);
		final PhysicalFile physicalFile = file.getPhysicalFile();
		final boolean isOrphan = this.fileDao.isOrphanPhysicalFile(physicalFile);
		this.fileDao.delete(file);
		if (isOrphan) {
			this.deleteFileInFileSystem(physicalFile);
			this.fileDao.deletePhysicalFile(physicalFile);
		}
	}

	private void deleteFileInFileSystem(final PhysicalFile physicalFile) {
		final Path path = Paths.get(physicalFile.getAbsolutePath());
		try {
			Files.deleteIfExists(path);
		} catch (final IOException e) {
			throw new FileUploadException(e);
		}
	}

	public File uploadFile(final InputStream inputStream, final String filename, final boolean publicAccess) {
		Objects.requireNonNull(inputStream);
		final File file = new File();
		final FileBucket fileBucket = publicAccess ? PUBLIC_BUCKET : PRIVATE_BUCKET;
		final PhysicalFile physicalFile = uploadFileToBucket(fileBucket, inputStream);
		final String contentHash = physicalFile.getContentHash();
		final String bucketName = fileBucket.getName();
		final PhysicalFile physicalFileInDdbb = this.fileDao.findPhysicalFileByHashAndBucket(contentHash, bucketName);
		if (physicalFileInDdbb != null) {
			file.setPhysicalFile(physicalFileInDdbb);
			deleteFileInFileSystem(physicalFile);
		} else {
			file.setPhysicalFile(physicalFile);
		}
		file.setName(filename);
		file.setCreated(LocalDateTime.now());
		file.setPublicAccess(publicAccess);
		this.fileDao.insert(file);
		return file;
	}
}
