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
	
	public File findById(Long id) {
		Objects.requireNonNull(id);
		return fileDao.find(File.class, id);
	}

	public InputStream downloadFile(final File file) {
		Objects.requireNonNull(file);
		try {
			final Path path = Paths.get(file.getPhysicalFile().getAbsolutePath());
			return new BufferedInputStream(Files.newInputStream(path));
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
		file.setName(newPhysicalFile.getUId());
		return file;
	}

	private PhysicalFile uploadFileToBucket(final FileBucket bucket, final InputStream inputStream) {
		final String uId = UUID.randomUUID().toString();
		final Path relativePath = this.getRelativePath(bucket, uId);
		final Path absolutePath = this.getAbsolutePath(relativePath);
		final String hash = this.uploadFileToFilesystem(absolutePath, inputStream);
		final PhysicalFile newPhysicalFile = new PhysicalFile();
		newPhysicalFile.setUId(uId);
		newPhysicalFile.setBucket(bucket.getName());
		newPhysicalFile.setContentHash(hash);
		newPhysicalFile.setAbsolutePath(absolutePath);
		newPhysicalFile.setCreated(LocalDateTime.now());
		newPhysicalFile.setRelativePath(relativePath);
		return newPhysicalFile;
	}

	private Path getRelativePath(final FileBucket fileBucket, final String uId) {
		return new PathBuilder().buildRelativePath(fileBucket, uId);
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
