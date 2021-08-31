package es.nivel36.laie.view.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.view.AbstractView;
import es.nivel36.laie.file.FileDto;
import es.nivel36.laie.file.FileService;
import es.nivel36.laie.user.UserDto;
import es.nivel36.laie.user.UserService;

@Named
@ViewScoped
public class ConfigView extends AbstractView {

	private static final long serialVersionUID = -5554897519730883905L;

	private static final Logger logger = LoggerFactory.getLogger(ConfigView.class);

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	@Inject
	private FileService fileService;

	private boolean imageChanged;

	private UserDto user;

	private FileDto oldUserImage;

	private FileDto userImage;

	@Inject
	private transient UserService userService;

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	public void captureImage(final CaptureEvent event) {
		Objects.requireNonNull(event);
		this.imageChanged = true;
		final byte[] data = event.getData();
		if (data == null) {
			return;
		}
		logger.debug("Upload camera image for user {} action performed", this.user);
		try (final InputStream inputStream = new ByteArrayInputStream(data)) {
			this.userImage = this.fileService.uploadFile(inputStream, "userImage_" + this.user.getUid());
		} catch (final IOException e) {
			this.imageChanged = false;
			throw new UncheckedIOException(e);
		}
	}

	public void changeLanguage() {
		final Locale newLocale = this.user.getLocale();
		logger.debug("Changed locale to {} for user {} action performed", newLocale, this.user);
		this.facesContext.getViewRoot().setLocale(newLocale);
	}

	private void changeUserImage() {
		try (final InputStream inputStream = this.fileService.downloadFile(this.userImage.getUid())) {
			this.userService.addImage(this.user.getUid(), this.userImage.getUid());
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void deleteImage() {
		if (this.userImage != null) {
			this.imageChanged = true;
			this.userImage = null;
		}
	}

	public UserDto getUser() {
		return this.user;
	}

	public FileDto getUserImage() {
		return this.userImage;
	}

	@PostConstruct
	public void init() {
		this.refreshUser();
		logger.debug("Config user {} init", this.user);
	}

	private void refreshUser() {
		this.sessionUser.refresh();
		this.user = this.sessionUser.get();
		this.userImage = this.user.getImage();
	}

	public void uploadImage(final FileUploadEvent event) throws IOException {
		Objects.requireNonNull(event);
		this.imageChanged = true;
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		logger.debug("Upload user {} image action performed", this.user);
		try (final InputStream inputStream = uploadedFile.getInputStream()) {
			this.userImage = this.fileService.uploadFile(inputStream, "userImage_" + this.user.getUid());
		} catch (final IOException e) {
			this.imageChanged = false;
			throw new UncheckedIOException(e);
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// SETTERS
	////////////////////////////////////////////////////////////////////////////

	public void setFileService(final FileService fileService) {
		this.fileService = fileService;
	}

	public void setUser(final UserDto user) {
		Objects.requireNonNull(user);
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}