package ged.ejb.core.file;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedInputStream;
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

import ged.ejb.core.util.ConfigurationProperty;

@Stateless
public class FileService {

	@Inject
	@ConfigurationProperty(value = "file.directory")
	private String fileDirectory;

	@Inject
	@ConfigurationProperty(value = "image.directory")
	private String imageDirectory;

	public InputStream getFile(final String uuid) {
		Objects.requireNonNull(uuid);
		try {
			final Path path = Paths.get(this.fileDirectory, uuid);
			try (InputStream is = Files.newInputStream(path); BufferedInputStream bis = new BufferedInputStream(is)) {
				return bis;
			}
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void removeFile(final String uuid) {
		Objects.requireNonNull(uuid);
		try {
			final Path path = Paths.get(this.fileDirectory, uuid);
			Files.deleteIfExists(path);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private String uploadFile(final String directory, final InputStream inputStream) {
		try {
			final String uuid = UUID.randomUUID().toString();
			final Path path = Paths.get(directory, uuid);
			Files.copy(inputStream, path, REPLACE_EXISTING);
			return uuid;
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public String uploadFile(final InputStream inputStream) {
		Objects.requireNonNull(inputStream);
		return this.uploadFile(this.fileDirectory, inputStream);
	}

	public String uploadImage(final InputStream inputStream) {
		Objects.requireNonNull(inputStream);
		return this.uploadFile(this.imageDirectory, inputStream);
	}
}
