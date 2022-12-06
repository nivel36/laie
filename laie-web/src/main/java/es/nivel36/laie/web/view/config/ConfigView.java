package es.nivel36.laie.web.view.config;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;

import org.omnifaces.util.Faces;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.file.FileUploadException;
import es.nivel36.laie.ejb.user.BadManagerException;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ConfigView extends AbstractView {

	private static final long serialVersionUID = -1963260899429126179L;

	private static final Logger logger = LoggerFactory.getLogger(ConfigView.class);

	public static final String URL = "/config.xhtml";

	private boolean imageChanged;

	private User user;

	private File userImage;

	@Inject
	private transient FileService fileService;

	@Inject
	private transient UserService userService;

	@PostConstruct
	public void init() {
		this.user = this.sessionUser.get();
		this.userImage = this.user.getPicture();
		logger.debug("Config user {} init", this.user);
	}

	public void captureImage(final CaptureEvent event) {
		Objects.requireNonNull(event);
		this.imageChanged = true;
		final byte[] data = event.getData();
		if (data == null) {
			return;
		}
		logger.debug("Upload camera image for user {} action performed", this.user);
		try (final InputStream inputStream = new ByteArrayInputStream(data);) {
			this.userImage = this.fileService.uploadTemporalFile(inputStream);
		} catch (final IOException e) {
			this.imageChanged = false;
			throw new FileUploadException(e);
		}
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
			this.userImage = this.fileService.uploadTemporalFile(inputStream);
		}
	}

	public void deleteImage() {
		if (this.userImage != null) {
			this.imageChanged = true;
			this.userImage = null;
		}
	}

	public void changeLanguage() {
		String language = this.user.getLanguage();
		final Locale newLocale = new Locale(language);
		logger.debug("Changed locale to {} for user {} action performed", newLocale, this.user);
		this.user.setLanguage(language);
		try {
			this.userService.updateUser(user);
		} catch (DuplicateEmailException e) {
			// No puede ocurrir puesto que solo estamos cambiando el idioma.
		} catch (BadManagerException e) {
			// No puede ocurrir puesto que solo estamos cambiando el idioma.
		}
		this.facesContext.getViewRoot().setLocale(newLocale);
	}

	public void save() {
		logger.debug("Save user {} action performed", this.user);
		try {
			this.saveImage();
			this.user = this.userService.updateUser(user);
			this.sessionUser.refresh();
			Faces.redirect("/index.xhtml");
		} catch (final DuplicateEmailException e) {
			this.addErrorToField("configForm:email", "user.error.email_exists");
		} catch (final BadManagerException e) {
			// No puede ocurrir
		}
	}

	private void saveImage() {
		if (!this.imageChanged) {
			return;
		}
		if (this.imageChanged && this.userImage == null) {
			this.user.setPicture(null);
			return;
		}
		logger.trace("Changing user image");
		try (final InputStream is = this.fileService.downloadFile(userImage);
				final BufferedInputStream bis = new BufferedInputStream(is)) {
			this.user = this.userService.changeUsersImage(this.user, is);
		} catch (final IOException e) {
			throw new FileUploadException(e);
		}
	}

	public User getUser() {
		return this.user;
	}

	public File getUserImage() {
		return this.userImage;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setFileService(final FileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}