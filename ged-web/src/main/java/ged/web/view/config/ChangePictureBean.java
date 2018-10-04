package ged.web.view.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
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

	@Inject
	private transient FileUploadService fileUploadService;

	private User user;

	@Inject
	private transient UserService userService;

	public void close() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		final Long userId = this.getIdFromParameters("userId");
		this.user = this.userService.find(userId);
	}

	public void onCapture(final CaptureEvent captureEvent) {
		Objects.requireNonNull(captureEvent);
		logger.debug("Capture user image from camara action performed");
		final byte[] data = captureEvent.getData();
		Objects.requireNonNull(data);
		try (InputStream inputStream = new ByteArrayInputStream(data)) {
			changeUserImage(inputStream);
		} catch (final IOException e) {
			throw new FacesException("Error in writing captured image.", e);
		}
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
		Objects.requireNonNull(uploadedFile);
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			changeUserImage(inputStream);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void changeUserImage(InputStream inputStream) {
		final String uuid = this.fileUploadService.uploadImage(inputStream);
		this.user.setImageFileName(uuid);
		this.user = this.userService.save(this.user);
		this.sessionUser.refresh();
	}
}
