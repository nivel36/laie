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

	private boolean showingCamera;

	private boolean showingImage;

	@Inject
	private transient FileUploadService fileUploadService;

	private User user;

	@Inject
	private transient UserService userService;

	public void cancel() {
		showImage();
	}

	public User getUser() {
		return user;
	}

	@PostConstruct
	public void init() {
		final Long userId = this.getIdFromParameters("userId");
		this.user = this.userService.find(userId);
		showImage();
	}

	public boolean isShowingCamera() {
		return showingCamera;
	}

	public boolean isShowingImage() {
		return showingImage;
	}

	public void onCapture(CaptureEvent captureEvent) {
		byte[] data = captureEvent.getData();
		try (InputStream inputStream = new ByteArrayInputStream(data)) {
			String imageFileName = fileUploadService.uploadImage(inputStream);
			user.setImageFileName(imageFileName);
			user = userService.save(user);
			sessionUser.refresh();
			showImage();
		} catch (IOException e) {
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
	
	public void close() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void showCamera() {
		this.showingCamera = true;
		this.showingImage = false;
	}

	public void showImage() {
		this.showingCamera = false;
		this.showingImage = true;
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
			user = userService.save(user);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
