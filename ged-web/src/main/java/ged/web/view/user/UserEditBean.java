package ged.web.view.user;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.Role;
import ged.ejb.user.User;
import ged.ejb.user.service.RoleService;
import ged.ejb.user.service.UserService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class UserEditBean extends AbstractPageBean {

	private static final long serialVersionUID = 1923340646020120203L;

	@Inject
	protected transient Logger logger;

	private User manager;

	@Inject
	private transient RoleService roleService;

	private User user;

	@Inject
	private transient UserService userService;

	public String cancel() {
		if (this.user.getId() == null) {
			this.logger.finer("Cancel create new user");
			return "userSearch.xhtml?faces-redirect=true";
		} else {
			this.logger.log(Level.FINER, "Cancel change user {0}", this.user.getId());
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
		this.logger.finest("Init UserEditBean");
		if (this.flash.containsKey("user")) {
			this.user = (User) this.flash.get("user");
			this.logger.log(Level.FINEST, "Flash scope contains user with name {0}", this.user.getFullName());
		} else {
			this.logger.log(Level.FINEST, "Flash scope is empty");
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

	private void newManager() {
		this.manager = new User();
		this.manager.setName("");
		this.manager.setSurename("");
	}

	public void removeManager() {
		newManager();
		this.user.setManager(null);
	}

	public String save() {
		this.logger.log(Level.FINE, "Saving user {0}", this.user.getFullName());
		this.user.setUser(this.sessionBean.getUser());
		if (this.manager.getUsername() != null) {
			this.logger.log(Level.FINER, "The user has no manager");
			this.user.setManager(this.manager);
		}
		if (this.user.getId() != null) {
			this.user = this.userService.update(this.user);
		} else {
			this.userService.insert(this.user);
		}
		return "userView.xhtml?id=" + this.user.getId() + "&faces-redirect=true";
	}

	public void setManager(final User manager) {
		this.manager = manager;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		this.logger.log(Level.FINEST, "Checking email");
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
			this.logger.log(Level.FINEST, "The email exists");
			final String msg = translate("user.error.email_exists");
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
			final String msg = translate("user.error.role");
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
			// y no hace falta que validemos si el registro existe (que oor otra
			// parte sí lo estará)
			return;
		}
		if (this.userService.usernameExists(username)) {
			this.logger.log(Level.FINEST, "The username exists");
			final String msg = translate("user.error.username_exists");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
}