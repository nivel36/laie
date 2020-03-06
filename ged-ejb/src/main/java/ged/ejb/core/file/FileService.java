package ged.ejb.core.file;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
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

	@Inject
	@Repository
	private FileJpaDao fileDao;

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	private Path buildAbsolutePath(final String uuid, final boolean publicAccess) {
		return Paths.get(this.fileDirectory, this.buildRelativePath(uuid, publicAccess).toString());
	}

	private Path buildRelativePath(final String uuid, final boolean publicAccess) {
		if (publicAccess) {
			return Paths.get("public", uuid.substring(0, 1), uuid.substring(1, 2), uuid);
		} else {
			return Paths.get("private", uuid.substring(0, 1), uuid.substring(1, 2), uuid);
		}
	}
	
	public File findById(long fileId) {
		if (fileId < 1) {
			logger.warn("Bad file id {}", fileId);
			throw new IllegalArgumentException("Bad file id: " + fileId);
		}
		logger.debug("Find file by id {}", fileId);
		return this.fileDao.find(fileId);
	}

	public InputStream getFile(final File file) {
		Objects.requireNonNull(file);
		try {
			final Path path = Paths.get(file.getPhysicalFile().getAbsolutePath());
			final InputStream is = Files.newInputStream(path);
			final BufferedInputStream bis = new BufferedInputStream(is);
			return bis;
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void removeFile(final File file) {
		Objects.requireNonNull(file);
		final PhysicalFile physicalFile = file.getPhysicalFile();
		final boolean isOrphan = this.fileDao.isOrphanPhysicalFile(physicalFile);
		if (isOrphan) {
			this.removeFileFromFilesystem(physicalFile);
		}
		this.fileDao.delete(file);
	}

	private void removeFileFromFilesystem(final PhysicalFile physicalFile) {
		try {
			final Path path = Paths.get(physicalFile.getAbsolutePath());
			Files.deleteIfExists(path);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public File uploadFile(final InputStream inputStream, final String filename, final boolean publicAccess) {
		Objects.requireNonNull(inputStream);

		final File file = new File(filename);
		file.setPublicAccess(publicAccess);

		final String uuid = UUID.randomUUID().toString();
		final Path absolutePath = this.buildAbsolutePath(uuid, publicAccess);

		final String hash = this.uploadFileToFilesystem(absolutePath, inputStream);
		final PhysicalFile physicalFile = this.fileDao.findPhysicalFileByHash(hash);

		if (physicalFile != null) {
			file.setPhysicalFile(physicalFile);
			try {
				Files.deleteIfExists(absolutePath);
			} catch (final IOException e) {
				throw new FileUploadException(e);
			}
		} else {
			final PhysicalFile newPhysicalFile = new PhysicalFile();
			newPhysicalFile.setUuid(uuid);
			newPhysicalFile.setContentHash(hash);
			newPhysicalFile.setAbsolutePath(absolutePath);
			newPhysicalFile.setCreated(LocalDateTime.now());
			final Path relativePath = this.buildRelativePath(uuid, publicAccess);
			newPhysicalFile.setRelativePath(relativePath);
			file.setPhysicalFile(newPhysicalFile);
		}

		this.fileDao.save(file);
		return file;
	}

	private String uploadFileToFilesystem(final Path path, final InputStream inputStream) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
				final Path parent = path.getParent();
				if (!Files.exists(parent)) {
					Files.createDirectories(parent);
				}
				Files.copy(inputStream, path, REPLACE_EXISTING);
				final byte[] hash = digest.digest();
				return Base64.getEncoder().encodeToString(hash);
			} catch (final IOException e) {
				throw new FileUploadException(e);
			}
		} catch (final NoSuchAlgorithmException e) {
			throw new FileUploadException(e);
		}
	}
}
