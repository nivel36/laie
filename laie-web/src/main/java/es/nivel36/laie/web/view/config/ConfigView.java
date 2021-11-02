package es.nivel36.laie.web.view.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class ConfigView extends AbstractView {

	private static final long serialVersionUID = -1963260899429126179L;

	private static final Logger logger = LoggerFactory.getLogger(ConfigView.class);

	private boolean imageChanged;

	private UserDto user;

	private String userImage;
	
	@Inject
	private FileService fileService;

	@Inject
	private transient UserService userService;
	
	@PostConstruct
	public void init() {
		this.user = this.sessionUser.get();
		this.userImage = this.user.getImageFileName();
		logger.debug("Config user {} init", this.user);
	}
	
	private void refreshUser() {
		this.user = this.sessionUser.get();
		this.userImage = this.user.getImageFileName();
		this.sessionUser.refresh();
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
			this.userImage = this.fileService.uploadTemporalFile(inputStream).getPath();
		} catch (final IOException e) {
			this.imageChanged = false;
			throw new UncheckedIOException(e);
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
			this.userImage = this.fileService.uploadTemporalFile(inputStream).getPath();
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
		}
		this.facesContext.getViewRoot().setLocale(newLocale);
	}
	
	public void save() {
		logger.debug("Save user {} action performed", this.user);
		try {
			this.userService.updateUser(user);
		}
		catch(final DuplicateEmailException e) {
			//TODO: gestionar error 
		}
		this.saveImage();
		this.refreshUser();
		this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}
	
	private void saveImage() {
		if (this.imageChanged) {
			if (this.userImage == null) {
				logger.trace("Deleting user image");
				this.deleteUserImage();
			} else {
				logger.trace("Changing user image");
				this.changeUserImage();
			}
		}
	}
	
	private void deleteUserImage() {
		this.userService.deleteUsersImage(this.user.getUid());
	}
	
	private void changeUserImage() {
		try (final InputStream inputStream = this.fileService.getFile(this.userImage)) {
			this.userService.changeUsersImage(this.user.getUid(), inputStream);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public UserDto getUser() {
		return this.user;
	}

	public String getUserImage() {
		return this.userImage;
	}

	public void setUser(final UserDto user) {
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