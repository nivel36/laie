package ged.web.view.user;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

	private User user;

	@Inject
	private transient UserService userService;

	public String cancel() {
		logger.trace("Cancel new user action performed");
		return "userSearch.xhtml?faces-redirect=true";
	}

	public List<User> completeManager(final String query) {
		return this.userService.search(query);
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
		logger.debug("Remove manager action performed");
		newManager();
		this.user.setManager(null);
	}

	public String save() {
		logger.debug("Save user action performed");
		final User newUser = this.userService.create(this.user.getName(), this.user.getSurename(), this.user.getEmail(),
				this.user.getRole(), this.manager);
		this.userService.save(newUser);
		return "userView.xhtml?id=" + newUser.getId() + "&faces-redirect=true";
	}

	public void selectManager(final User manager) {
		this.manager = manager;
		addInfoMessage("user.manager_added", new Object[] { this.manager.getFullName() });
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

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value) {
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

	public void validateManager(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null) {
			return;
		}
		if (this.manager.equals(this.user)) {
			logger.debug("User can't be his/her manager");
			final String msg = TransaltionUtils.translate("user.error.manager");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateRole(final FacesContext context, final UIComponent component, final Object value) {
		if (this.manager.getEmail() == null) {
			return;
		}
		final Role userRole = (Role) value;
		final Role managerRole = this.manager.getRole();
		if (!isAvalidRole(userRole, managerRole)) {
			final String msg = TransaltionUtils.translate("user.error.role");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
}