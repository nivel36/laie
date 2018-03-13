package ged.ejb.core;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
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

import ged.ejb.UploadedServerFile;
import ged.ejb.core.util.ConfigurationProperty;

@Stateless
public class FileUploadServiceImpl implements FileUploadService {

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
			final Path newPath = Files.move(source, source.resolveSibling(file.getName()), REPLACE_EXISTING);
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

	private String upload(final String directory, final InputStream inputStream) {
		try {
			final String uuid = UUID.randomUUID().toString();
			Files.copy(inputStream, new File(directory, uuid).toPath(), REPLACE_EXISTING);
			return uuid;
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public String uploadFile(final InputStream inputStream) {
		Objects.requireNonNull(inputStream);
		return this.upload(this.fileDirectory, inputStream);
	}

	@Override
	public String uploadImage(final InputStream inputStream) {
		Objects.requireNonNull(inputStream);
		return this.upload(this.imageDirectory, inputStream);
	}
}
