package ged.web.view.user;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.FileUploadService;
import ged.ejb.core.model.Page;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserEditBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	private String cancelUrl;

	@Inject
	private transient FileUploadService fileUploadService;

	private User user;

	@Inject
	private transient UserService userService;

	private void buildUser() {
		this.user = new User();
		this.user.setLanguage("ES");
		this.user.setRowsPerPage(25);
		this.user.setDateOfJoin(LocalDate.now());
		this.user.setOwner(this.user);
	}

	public String cancel() {
		logger.debug("Cancel edit action performed");
		return this.cancelUrl;
	}

	public void changeRoleListener() {
		logger.trace("Change role listener triggered");
		if (isAdmin()) {
			this.user.setManager(null);
		}
	}

	private void editUserInit() {
		logger.debug("User {} edit init", this.user.getEmail());
		if (!this.sessionUser.hasPermissionToEdit(this.user)) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
		this.cancelUrl = "/faces/user/user?faces-redirect=true&userId=" + this.user.getId();
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		this.user = this.getValueFromFlash("user");
		if (this.user == null) {
			newUserInit();
		} else {
			editUserInit();
		}
	}

	public boolean isAdmin() {
		return this.user.isAdmin();
	}

	public boolean isNewUser() {
		return this.user.getId() == 0;
	}

	private void newUserInit() {
		logger.debug("New user edit init");
		if (!this.sessionUser.isAdmin()) {
			logger.error("User {} hasn't got priviliges to add a new user", this.sessionUser);
			throw new SecurityException();
		}
		buildUser();
		this.cancelUrl = "/faces/user/userSearch";
	}

	public String save() {
		logger.debug("Save user action performed");
		if (!this.sessionUser.hasPermissionToEdit(this.user)) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
		this.user = this.userService.save(this.user);
		return "/faces/user/user?faces-redirect=true&userId=" + this.user.getId();
	}

	public List<User> searchManager(final String query) {
		logger.trace("Searching for manager with the string {}", query);
		if (query == null || query.trim().length() < 3) {
			return new ArrayList<>();
		}
		final List<User> managers = this.userService.search(query, Page.ALL);
		managers.remove(this.user);
		return managers;
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
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null) {
			return;
		}
		final String userEmail = (String) value;
		logger.trace("Validating email {}", userEmail);
		if (userEmail.equals(this.user.getEmail())) {
			// If the old and the new email are equals, the user is not updating the email.
			return;
		}
		if (this.userService.isEmailInUse(userEmail)) {
			logger.warn("Email {} exists", userEmail);
			final String msg = this.translator.message("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, msg, msg));
		}
	}
}
