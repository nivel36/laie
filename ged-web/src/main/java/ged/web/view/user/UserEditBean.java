package ged.web.view.user;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;
import ged.web.core.util.TransaltionUtils;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class UserEditBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1923340646020120203L;

	private User manager;

	@Inject
	private transient RoleService roleService;

	private boolean skip;

	private User user;

	@Inject
	private transient UserService userService;

	public String cancel() {
		if (this.user.getId() == 0) {
			logger.trace("Cancel new user action performed");
			return "userSearch.xhtml?faces-redirect=true";
		} else {
			logger.debug("Cancel change user action performed");
			return "userView.xhtml?id=" + this.user.getId() + "&faces-redirect=true";
		}
	}

	public User getManager() {
		return this.manager;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	private void init() {
		logger.trace("Init UserEditBean");
		if (this.flash.containsKey("user")) {
			this.user = (User) this.flash.get("user");
			logger.debug("Flash scope contains user with name {}", this.user.getFullName());
		} else {
			logger.debug("Flash scope is empty");
			this.user = new User();
			final int rowsPerPage = this.sessionBean.getRowsPerPage();
			this.user.setRowsPerPage(rowsPerPage);
			final Locale locale = this.facesContext.getApplication().getDefaultLocale();
			final String language = locale.getLanguage();
			this.user.setLanguage(language);
			this.user.setPassword("M+SzETkPtT+deVQNIScBEXivvfozSne5QqIqyWICLv0=");
		}
		if (this.user.getManager() != null) {
			this.manager = this.user.getManager();
		} else {
			newManager();
		}
		this.flash.put("user", this.user);
	}

	private boolean isAvalidRole(final Role userRole, final Role managerRole) {
		if (userRole.getName().equals(managerRole.getName())) {
			return true;
		}
		return this.roleService.isASubordinateRole(managerRole, userRole);
	}

	public boolean isSkip() {
		return this.skip;
	}

	private void newManager() {
		this.manager = new User();
		this.manager.setName("");
		this.manager.setSurename("");
	}

	public String onFlowProcess(final FlowEvent event) {
		if (this.skip) {
			this.skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public void removeManager() {
		logger.debug("Remove manager action performed");
		newManager();
		this.user.setManager(null);
	}

	public String save() {
		logger.debug("Save user action performed");
		setManager();
		this.userService.save(this.user);
		return "userView.xhtml?id=" + this.user.getId() + "&faces-redirect=true";
	}

	private void setManager() {
		if (this.manager.getUsername() != null) {
			logger.trace("The user has no manager");
			this.user.setManager(this.manager);
		}
	}

	public void setManager(final User manager) {
		this.manager = manager;
	}

	public void setRoleService(final RoleService roleService) {
		this.roleService = roleService;
	}

	public void setSkip(final boolean skip) {
		this.skip = skip;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		logger.debug("Checking email");
		if (value == null) {
			return;
		}
		final String email = (String) value;
		if (value.equals(this.user.getEmail())) {
			// Si el valor del email es el mismo que el que estamos validando
			// es porque estamos actualizando un valor (que no es el email)
			// y no hace falta que validemos si el registro existe (que oor otra
			// parte sí lo estará)
			return;
		}
		if (this.userService.emailExists(email)) {
			logger.debug("The email exists");
			final String msg = TransaltionUtils.translate("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateManager(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		if (value == null) {
			return;
		}
		if (this.manager.equals(this.user)) {
			logger.debug("User can't be his/her manager");
			final String msg = TransaltionUtils.translate("user.error.manager");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateRole(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		if (this.manager.getUsername() == null) {
			return;
		}
		final Role userRole = (Role) value;
		final Role managerRole = this.manager.getRole();
		if (!isAvalidRole(userRole, managerRole)) {
			final String msg = TransaltionUtils.translate("user.error.role");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateUsername(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		if (value == null) {
			return;
		}
		final String username = (String) value;
		if (value.equals(this.user.getUsername())) {
			// Si el valor del usuario es el mismo que el que estamos validando
			// es porque estamos actualizando un valor (que no es el de usuario)
			// y no hace falta que validemos si el registro existe (que por otra
			// parte sí lo estará)
			return;
		}
		if (this.userService.usernameExists(username)) {
			logger.debug("The username exists");
			final String msg = TransaltionUtils.translate("user.error.username_exists");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
}