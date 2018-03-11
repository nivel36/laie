package ged.web.core.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
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

	/* (non-Javadoc)
	 * @see ged.web.core.view.FileUploadService#getFileFromFileSystem(ged.ejb.UploadedServerFile)
	 */
	@Override
	public File getFileFromFileSystem(final UploadedServerFile file) throws IOException {
		final File downloableFile = new File(file.getName());
		final boolean renamed = new File(this.fileDirectory, file.getUuid()).renameTo(downloableFile);
		if (!renamed) {
			logger.error("Can't rename the file");
			throw new IOException("Can't rename the file");
		}
		return downloableFile;
	}

	/* (non-Javadoc)
	 * @see ged.web.core.view.FileUploadService#removeFileFromFileSystem(java.lang.String)
	 */
	@Override
	public void removeFileFromFileSystem(final String uuid) throws IOException {
		Files.deleteIfExists(new File(this.fileDirectory, uuid).toPath());
	}

	private String upload(final String directory, final UploadedFile file) throws IOException {
		final String uuid = UUID.randomUUID().toString();
		try (InputStream input = file.getInputstream()) {
			Files.copy(input, new File(directory, uuid).toPath());
		}
		return uuid;
	}

	/* (non-Javadoc)
	 * @see ged.web.core.view.FileUploadService#uploadFile(org.primefaces.model.UploadedFile)
	 */
	@Override
	public String uploadFile(final UploadedFile file) throws IOException {
		Objects.requireNonNull(file);
		logger.debug("Uploading file {}", file.getFileName());
		return upload(this.fileDirectory, file);
	}

	/* (non-Javadoc)
	 * @see ged.web.core.view.FileUploadService#uploadImage(org.primefaces.model.UploadedFile)
	 */
	@Override
	public String uploadImage(final UploadedFile file) throws IOException {
		Objects.requireNonNull(file);
		logger.debug("Uploading image {}", file.getFileName());
		return upload(this.imageDirectory, file);
	}
}
