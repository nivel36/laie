package ged.web.view.user;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.FileUploadService;
import ged.ejb.user.User;
import ged.ejb.user.UserException;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserEditBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	@Inject
	private transient FileUploadService fileUploadService;

	@Inject
	private transient RoleService roleService;

	private User user;

	@Inject
	private transient UserService userService;

	public void cleanManager() {
		this.user.setManager(null);
	}

	public List<User> completeManager(final String query) {
		if ((query == null) || (query.trim().length() < 3)) {
			return new ArrayList<>();
		}
		final List<User> managers = this.userService.search(query);
		managers.remove(this.user);
		return managers;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		logger.debug("UserEditBean init");
		this.user = this.getValueFromFlash("user");
		if (this.user == null) {
			this.user = new User();
		}
	}

	private void insertUser() {
		this.userService.insert(this.user);
	}

	private boolean isAvalidRole(final Role userRole, final Role managerRole) {
		if (userRole.getName().equals(managerRole.getName())) {
			// They got the same role.
			return true;
		}
		return this.roleService.isASubordinateRole(managerRole, userRole);
	}

	public boolean isNewUser() {
		if (this.user == null) {
			throw new IllegalStateException("Null user");
		}
		return this.user.getId() == 0;
	}

	public String save() {
		logger.debug("Save user action performed");
		if (this.isNewUser()) {
			this.insertUser();
		}
		else {
			this.updateUser();
		}
		return "/faces/user/user?faces-redirect=true&userId=" + this.user.getId();
	}

	public void setFileUploadService(final FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public void setRoleService(final RoleService roleService) {
		this.roleService = roleService;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	private void updateUser() {
		try {
			this.user = this.userService.update(this.user);
		}
		catch (final EJBException e) {
			if (e.getCause() instanceof UserException) {
				logger.error("Trying to change the role to the last admin on the app");
				final Role admin = this.roleService.findAdmin();
				this.user.setRole(admin);
				this.addMessage(SEVERITY_ERROR, "user.error.last_admin", "user.error.last_admin");
			}
			else {
				throw e;
			}
		}
	}

	public void uploadImage(final FileUploadEvent event) throws IOException {
		Objects.requireNonNull(event);
		if (event.getFile() == null) {
			return;
		}
		final String uuid = this.fileUploadService.uploadImage(event.getFile().getInputstream());
		this.user.setImageFileName(uuid);

	}

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value) {
		logger.debug("Checking email");
		if (value == null) {
			return;
		}
		final String email = (String) value;
		if (value.equals(this.user.getEmail())) {
			// If the old and the new email are equals, the user is not updating the email.
			return;
		}
		if (this.userService.emailExists(email)) {
			logger.debug("Validation error. The email {} exists", email);
			final String msg = this.translator.message("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateRole(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null) {
			return;
		}
		final User manager = this.user.getManager();
		if ((manager == null) || (manager.getEmail() == null)) {
			// No manager setted, all roles are OK.
			return;
		}
		final Role userRole = (Role) value;
		final Role managerRole = manager.getRole();
		if (!this.isAvalidRole(userRole, managerRole)) {
			logger.debug("Validation error. The role {} for user {} is invalid", userRole.getName(), this.user.getName());
			final String msg = this.translator.message("user.error.role");
			throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, msg, msg));
		}
	}
}
