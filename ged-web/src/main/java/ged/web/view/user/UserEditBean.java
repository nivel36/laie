package ged.web.view.user;

import java.util.Locale;
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
import ged.ejb.user.RoleService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
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
		if (this.user.getId() == 0) {
			return "userSearch.xhtml?faces-redirect=true";
		} else {
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
		if (this.flash.containsKey("user")) {
			this.user = (User) this.flash.get("user");
		} else {
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
		if (this.manager.getUsername() != null) {
			this.user.setManager(this.manager);
		}
		if (this.user.getId() != 0) {
			this.user = this.userService.updateUser(this.user);
		} else {
			this.userService.insertUser(this.user);
		}
		this.actionsBean.add(this.user);
		return "userView.xhtml?id=" + this.user.getId() + "&faces-redirect=true";
	}

	public void setLogger(final Logger logger) {
		this.logger = logger;
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

	public void validateEmail(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		final String email = (String) value;
		if (value == null) {
			return;
		}
		if (value.equals(this.user.getEmail())) {
			return;
		}
		if (this.userService.emailExists(email)) {
			final String msg = translate("user.error.email_exists");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public void validateManager(final FacesContext context, final UIComponent component, final Object value)
			throws ValidatorException {
		if (this.manager.getUsername() == null) {
			return;
		}
		if (this.user.equals(this.manager)) {
			final String msg = translate("user.error.manager");
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
}