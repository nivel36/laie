package ged.web.core.view;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.UploadedServerFile;
import ged.web.core.util.ConfigurationProperty;

@Stateless
public class FileUploadServiceImpl implements FileUploadService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	@Inject
	@ConfigurationProperty(value = "image.directory")
	private String imageDirectory;

	@Override
	public File getFileFromFileSystem(final UploadedServerFile file) {
		Objects.requireNonNull(file);
		try {
			final Path source = Paths.get(this.fileDirectory, file.getUuid());
			final Path target = Paths.get(file.getName());
			final Path newPath = Files.move(source, target.resolve(source.getFileName()), REPLACE_EXISTING);
			return newPath.toFile();
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void removeFileFromFileSystem(final String uuid) {
		try {
			Files.deleteIfExists(new File(this.fileDirectory, uuid).toPath());
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private String upload(final String directory, final UploadedFile file) {
		final String uuid = UUID.randomUUID().toString();
		try (InputStream input = file.getInputstream()) {
			Files.copy(input, new File(directory, uuid).toPath());
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
		return uuid;
	}

	@Override
	public String uploadFile(final UploadedFile file) {
		Objects.requireNonNull(file);
		logger.debug("Uploading file {}", file.getFileName());
		return this.upload(this.fileDirectory, file);
	}

	@Override
	public String uploadImage(final UploadedFile file) {
		Objects.requireNonNull(file);
		logger.debug("Uploading image {}", file.getFileName());
		return this.upload(this.imageDirectory, file);
	}
}
