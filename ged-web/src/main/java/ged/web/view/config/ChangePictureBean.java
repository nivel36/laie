package ged.web.view.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.FileUploadService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;

@ViewScoped
@Named
public class ChangePictureBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -4692285273689581632L;

	private CroppedImage croppedImage;

	@Inject
	private transient FileUploadService fileUploadService;

	private User user;

	@Inject
	private transient UserService userService;

	public void cropImage() {
		if (croppedImage == null) {
			return;
		}
		logger.debug("Upload cropped user image action performed");
		try (InputStream inputStream = new ByteArrayInputStream(croppedImage.getBytes())) {
			final String uuid = this.fileUploadService.uploadImage(inputStream);
			this.user.setImageFileName(uuid);
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public User getUser() {
		return user;
	}

	@PostConstruct
	public void init() {
		final Long userId = this.getIdFromParameters("userId");
		this.user = this.userService.find(userId);
	}

	public void setCroppedImage(final CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	public void setFileUploadService(final FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public void uploadImage(final FileUploadEvent event) {
		Objects.requireNonNull(event);
		logger.debug("Upload user image action performed");
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			final String uuid = this.fileUploadService.uploadImage(inputStream);
			this.user.setImageFileName(uuid);
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
