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

import es.nivel36.laie.ejb.candidate.File;
import es.nivel36.laie.ejb.core.util.ConfigurationProperty;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class PhysicalFileService {

	private static final FileBucket TEMP_BUCKET = TemporalFileBucket.getInstance();

	private static final FileBucket PRIVATE_BUCKET = PrivateFileBucket.getInstance();

	private static final FileBucket PUBLIC_BUCKET = PublicFileBucket.getInstance();

	private @Inject PhysicalFileJpaDao fileDao;

	private @Inject @ConfigurationProperty(value = "file.directory") String fileDirectory;

	public File findById(Long id) {
		Objects.requireNonNull(id);
		return fileDao.find(File.class, id);
	}

	public InputStream downloadFile(final PhysicalFile file) {
		Objects.requireNonNull(file);
		try {
			final Path path = Paths.get(file.getAbsolutePath());
			return new BufferedInputStream(Files.newInputStream(path));
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public PhysicalFile uploadTemporalPhisicalFile( final InputStream inputStream) {
		final String uId = UUID.randomUUID().toString();
		final Path relativePath = this.getRelativePath(TEMP_BUCKET, uId);
		final Path absolutePath = this.getAbsolutePath(relativePath);
		final String hash = this.uploadFileToFilesystem(absolutePath, inputStream);
		final PhysicalFile newPhysicalFile = new PhysicalFile();
		newPhysicalFile.setUId(uId);
		newPhysicalFile.setBucket(TEMP_BUCKET.getName());
		newPhysicalFile.setContentHash(hash);
		newPhysicalFile.setAbsolutePath(absolutePath);
		newPhysicalFile.setCreated(LocalDateTime.now());
		newPhysicalFile.setRelativePath(relativePath);
		this.fileDao.insert(newPhysicalFile);
		return newPhysicalFile;
	}
	
	public void moveFromTemporalFile( final PhysicalFile file, boolean publicAccess) {
		final FileBucket bucket = publicAccess ? PUBLIC_BUCKET : PRIVATE_BUCKET;
		final Path relativePath = this.getRelativePath(bucket, file.getUId());
		final Path absolutePath = this.getAbsolutePath(relativePath);
		try {
			Files.copy(Path.of(file.getAbsolutePath()), absolutePath);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		file.setAbsolutePath(absolutePath);
		file.setBucket(bucket.getName());
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

	public void removeFile(final PhysicalFile file) {
		Objects.requireNonNull(file);
		final boolean isOrphan = this.fileDao.isOrphanPhysicalFile(file);
		if (isOrphan) {
			this.deleteFileInFileSystem(file);
			this.fileDao.deletePhysicalFile(file);
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

	public PhysicalFile uploadFile(final InputStream inputStream, final String filename, final boolean publicAccess) {
		Objects.requireNonNull(inputStream);
		final FileBucket fileBucket = publicAccess ? PUBLIC_BUCKET : PRIVATE_BUCKET;
		final PhysicalFile physicalFile = uploadFileToBucket(fileBucket, inputStream);
		final String contentHash = physicalFile.getContentHash();
		final String bucketName = fileBucket.getName();
		final PhysicalFile physicalFileInDdbb = this.fileDao.findPhysicalFileByHashAndBucket(contentHash, bucketName);
		if (physicalFileInDdbb != null) {
			deleteFileInFileSystem(physicalFile);
			return physicalFileInDdbb;
		} else {
			return physicalFile;
		}
	}
}
