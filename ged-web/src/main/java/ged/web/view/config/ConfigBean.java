package ged.web.view.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.FileUploadService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ConfigBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2789492893353263506L;

	@Inject
	private transient FileUploadService fileUploadService;

	private User user;

	@Inject
	private transient UserService userService;

	public void changeLocaleListener() {
		facesContext.getViewRoot().setLocale(new Locale(user.getLanguage()));
	}

	private void changeSessionUser() {
		this.sessionUser.refresh();
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		this.user = this.sessionUser.get();
		sessionUser.refresh();
		logger.trace("Config user {} init", user.getEmail());
	}

	public void openChangePasswordDialog() {
		this.openDialog("/faces/config/changePasswordDialog", this.buildDialogParameter("userId", String.valueOf(this.user.getId())));
	}

	public void openChangePictureDialog() {
		this.openDialog("/faces/config/changePictureDialog", this.buildDialogParameter("userId", String.valueOf(this.user.getId())));
	}

	public void save() {
		logger.debug("Save user {} action performed", user);
		final long userId = this.user.getId();
		if (userId == this.sessionUser.get().getId()) {
			this.changeSessionUser();
		}
		this.user = this.userService.save(this.user);
		this.sessionUser.refresh();
		this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public void uploadImage(final FileUploadEvent event) {
		Objects.requireNonNull(event);
		logger.debug("Upload user {} image action performed", user);
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
	
	public void captureImage(final CaptureEvent event) {
		Objects.requireNonNull(event);
		logger.debug("Upload camera image action performed");
		 byte[] data = event.getData();
		if (data == null) {
			return;
		}
		try (InputStream inputStream = new ByteArrayInputStream(data);) {
			final String uuid = this.fileUploadService.uploadImage(inputStream);
			this.user.setImageFileName(uuid);
		}
		catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}