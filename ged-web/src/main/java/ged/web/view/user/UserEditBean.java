package ged.web.view.user;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

import java.io.IOException;
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
import ged.ejb.user.DuplicateEmailException;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserEditBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	private String cancelUrl;

	private UIComponent emailComponent;

	@Inject
	private transient FileUploadService fileUploadService;

	private User user;

	@Inject
	private transient UserService userService;

	private void buildUser() {
		this.user = new User();
		this.user.setLanguage("ES");
		this.user.setRowsPerPage(25);
		this.user.setPassword("M+SzETkPtT+deVQNIScBEXivvfozSne5QqIqyWICLv0=".toCharArray());
		this.user.setDateOfJoin(LocalDate.now());
		this.user.setOwner(this.user);
	}

	public String cancel() {
		return this.cancelUrl;
	}

	public void changeRoleListener() {
		if (user.isAdmin()) {
			cleanManager();
		}
	}

	public void cleanManager() {
		this.user.setManager(null);
	}

	private void editUser() {
		if (!sessionUser.hasPermissionToEdit(this.user)) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
		this.cancelUrl = "/faces/user/user?faces-redirect=true&userId=" + this.user.getId();
	}

	public UIComponent getEmailComponent() {
		return emailComponent;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		logger.debug("UserEditBean init");
		this.user = this.getValueFromFlash("user");
		if (this.user == null) {
			newUser();
		}
		else {
			editUser();
		}
	}

	private void newUser() {
		if (!this.sessionUser.isAdmin()) {
			logger.error("User {} hasn't got priviliges to add a new user", sessionUser);
			throw new SecurityException();
		}
		this.buildUser();
		this.cancelUrl = "/faces/user/userSearch";
	}

	public String save() {
		logger.debug("Save user action performed");
		if (!sessionUser.hasPermissionToEdit(this.user)) {
			logger.error("User {} hasn't got priviliges to edit user {}", this.sessionUser.get(), this.user);
			throw new SecurityException();
		}
		try {
			this.user = this.userService.save(this.user);
			return "/faces/user/user?faces-redirect=true&userId=" + this.user.getId();
		}
		catch (final DuplicateEmailException e) {
			addErrorToField(emailComponent, "user.error.email_exists");
			return "";
		}
	}

	public List<User> searchManager(final String query) {
		if ((query == null) || (query.trim().length() < 3)) {
			return new ArrayList<>();
		}
		final List<User> managers = this.userService.search(query);
		managers.remove(this.user);
		return managers;
	}

	public void setEmailComponent(final UIComponent emailComponent) {
		this.emailComponent = emailComponent;
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

	public void uploadImage(final FileUploadEvent event) throws IOException {
		Objects.requireNonNull(event);
		final UploadedFile uploadedFile = event.getFile();
		if (uploadedFile == null) {
			return;
		}
		final String uuid = this.fileUploadService.uploadImage(uploadedFile.getInputstream());
		this.user.setImageFileName(uuid);
	}

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value) {
		logger.debug("Checking email");
		if (value == null) {
			return;
		}
		final String userEmail = (String) value;
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
