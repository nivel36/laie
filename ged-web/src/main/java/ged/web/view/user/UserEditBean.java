package ged.web.view.user;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

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
import ged.web.core.util.Translate;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserEditBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -2187385732087309689L;

	@Inject
	private transient FileUploadService fileUploadService;

	private User manager;

	@Inject
	private transient RoleService roleService;

	private User user;

	@Inject
	private transient UserService userService;

	public List<User> completeManager(final String query) {
		if (query.trim().length() > 2) {
			final List<User> managers = this.userService.search(query);
			managers.remove(this.user);
			return managers;
		}
		else {
			return new ArrayList<>();
		}
	}

	public User getManager() {
		return this.manager;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		this.user = this.getValueFromFlash("user");
		if (this.user == null) {
			this.user = new User();
		}
		this.manager = this.user.getManager();
	}

	private void insertUser() {
		this.userService.insert(this.user);
	}

	private boolean isAvalidRole(final Role userRole, final Role managerRole) {
		if (userRole.getName().equals(managerRole.getName())) {
			// Got the same role.
			return true;
		}
		return this.roleService.isASubordinateRole(managerRole, userRole);
	}

	public boolean isNewUser() {
		return this.user.getId() == 0;
	}

	public String save() {
		logger.debug("Save user action performed");
		this.user.setManager(this.manager);
		if (this.user.getId() == 0) {
			this.insertUser();
		}
		else {
			this.updateUser();
		}
		return String.format("/faces/user/user?userId=%d&faces-redirect=true", this.user.getId());

	}

	public void setFileUploadService(final FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public void setManager(final User manager) {
		this.manager = manager;
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
		logger.debug("Update user action performed");
		try {
			this.user = this.userService.update(this.user);
		}
		catch (final EJBException e) {
			if (e.getCause() instanceof UserException) {
				final Role admin = this.roleService.findAdmin();
				this.user.setRole(admin);
				this.addMessage(FacesMessage.SEVERITY_ERROR, "user.error.last_admin", "user.error.last_admin");
			}
			else {
				throw e;
			}
		}
	}

	public void uploadImage(final FileUploadEvent event) throws IOException {
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
			// Si los emails son iguales es porque no lo estamos actualizando por lo que no
			// hace validación
			return;
		}
		if (this.userService.emailExists(email)) {
			logger.debug("The email exists");
			final String msg = Translate.message("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateRole(final FacesContext context, final UIComponent component, final Object value) {
		if ((this.manager == null) || (this.manager.getEmail() == null)) {
			return;
		}
		final Role userRole = (Role) value;
		final Role managerRole = this.manager.getRole();
		if (!this.isAvalidRole(userRole, managerRole)) {
			final String msg = Translate.message("user.error.role");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
}
