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
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ConfigView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	private transient FileUploadService fileUploadService;

	private User user;

	@Inject
	private transient UserService userService;

	public void captureImage(final CaptureEvent event) {
		Objects.requireNonNull(event);
		logger.debug("AbstractAction: Upload camera image for user {}", this.user);
		final byte[] data = event.getData();
		if (data == null) {
			return;
		}
		try (InputStream inputStream = new ByteArrayInputStream(data);) {
			final String uuid = this.fileUploadService.uploadImage(inputStream);
			this.user.setImageFileName(uuid);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void changeLocale() {
		final Locale newLocale = new Locale(this.user.getLanguage());
		logger.debug("AbstractAction: Changed locale to {} for user {}", newLocale, this.user);
		this.facesContext.getViewRoot().setLocale(newLocale);
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		this.refreshSessionUser();
		this.user = this.sessionUser.get();
		logger.debug("Config user {}", this.user.getEmail());
	}

	public void openChangePasswordDialog() {
		this.openDialog("/config/changePasswordDialog",
				this.buildDialogParameter("userId", String.valueOf(this.user.getId())));
	}

	private void refreshSessionUser() {
		this.sessionUser.refresh();
	}

	public void save() {
		logger.debug("AbstractAction: Save user {} data", this.user);
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

	public void uploadImage(final FileUploadEvent event) {
		Objects.requireNonNull(event);
		logger.debug("AbstractAction: Upload user {} image", this.user);
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		try (InputStream inputStream = uploadedFile.getInputstream()) {
			final String uuid = this.fileUploadService.uploadImage(inputStream);
			this.user.setImageFileName(uuid);
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}