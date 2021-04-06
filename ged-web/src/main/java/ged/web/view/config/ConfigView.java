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
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.file.File;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ConfigView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private User user;

	@Inject
	private transient UserService userService;

	public void captureImage(final CaptureEvent event) {
		Objects.requireNonNull(event);
		final byte[] data = event.getData();
		if (data == null) {
			return;
		}
		logger.debug("Upload camera image for user {} action performed", this.user);
		try (final InputStream inputStream = new ByteArrayInputStream(data);) {
			final File image = this.userService.addUserImage(this.user.getUid(), inputStream);
			this.user.setPicture(image);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void changeLanguage() {
		final Locale newLocale = new Locale(this.user.getLanguage());
		logger.debug("Changed locale to {} for user {} action performed", newLocale, this.user);
		this.facesContext.getViewRoot().setLocale(newLocale);
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		this.refreshSessionUser();
		this.user = this.sessionUser.get();
		logger.debug("Config user {} init", this.user);
	}

	private void refreshSessionUser() {
		this.sessionUser.refresh();
	}

	public void save() {
		logger.debug("Save user {} action performed", this.user);
		this.user = this.userService.save(this.user);
		this.refreshSessionUser();
		this.addMessage(FacesMessage.SEVERITY_INFO, "action.save_action_performed", "action.save_action_performed");
	}

	public void setUser(final User user) {
		Objects.requireNonNull(user);
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	public void uploadImage(final FileUploadEvent event) throws IOException {
		Objects.requireNonNull(event);
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		logger.debug("Upload user {} image action performed", this.user);
		try (final InputStream inputStream = uploadedFile.getInputStream()) {
			final File image = this.userService.addUserImage(this.user.getUid(), inputStream);
			this.user.setPicture(image);
		}
	}
}