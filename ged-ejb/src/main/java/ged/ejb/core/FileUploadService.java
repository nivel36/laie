package ged.ejb.core;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.file.ServerFile;
import ged.ejb.core.util.ConfigurationProperty;

@Stateless
public class FileUploadService {

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	@Inject
	@ConfigurationProperty(value = "image.directory")
	private String imageDirectory;

	public File getFileFromFileSystem(final ServerFile file) {
		Objects.requireNonNull(file);
		try {
			final Path source = Paths.get(this.fileDirectory, file.getUuid());
			final Path newPath = Files.move(source, source.resolveSibling(file.getName()), REPLACE_EXISTING);
			return newPath.toFile();
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void removeFileFromFileSystem(final String uuid) {
		try {
			Files.deleteIfExists(new File(this.fileDirectory, uuid).toPath());
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private String upload(final String directory, final InputStream inputStream) {
		try {
			final String uuid = UUID.randomUUID().toString();
			Files.copy(inputStream, new File(directory, uuid).toPath(), REPLACE_EXISTING);
			return uuid;
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public String uploadFile(final InputStream inputStream) {
		Objects.requireNonNull(inputStream);
		return this.upload(this.fileDirectory, inputStream);
	}

	public String uploadImage(final File image) {
		try (InputStream inputStream = new FileInputStream(image)) {
			Objects.requireNonNull(inputStream);
			return this.upload(this.imageDirectory, inputStream);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public String uploadImage(final InputStream inputStream) {
		Objects.requireNonNull(inputStream);
		return this.upload(this.imageDirectory, inputStream);
	}
}
