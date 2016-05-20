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
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class UserEditBean extends AbstractPageBean {

	private static final long serialVersionUID = 1923340646020120203L;

	private String email;

	@Inject
	protected transient Logger logger;

	private User manager;

	protected boolean modal = true;

	private String name;

	private Paginator<User> paginator;

	protected boolean rendered = false;

	private String surename;

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

	public void cancelPopup() {
		this.rendered = false;
	}

	public void cleanPopup() {
		clearPopupFields();
		search();
	}

	private void clearPopupFields() {
		this.email = null;
		this.name = null;
		this.surename = null;
	}

	public String getEmail() {
		return this.email;
	}

	public User getManager() {
		return this.manager;
	}

	public String getName() {
		return this.name;
	}

	public Paginator<User> getPaginator() {
		return this.paginator;
	}

	public String getSurename() {
		return this.surename;
	}

	public User getUser() {
		return this.user;
	}

	public void hide() {
		clearPopupFields();
		this.rendered = false;
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
			this.manager = new User();
			this.manager.setName("");
			this.manager.setSurename("");
		}
		this.flash.put("user", this.user);
		this.paginator = new Paginator<User>();
		this.paginator.setRowsPerPage(this.sessionBean.getUser().getRowsPerPage());
	}

	public boolean isModal() {
		return this.modal;
	}

	public boolean isRendered() {
		return this.rendered;
	}

	public void open() {
		this.rendered = true;
	}

	public void removeManager() {
		this.manager = new User();
		this.manager.setName("");
		this.manager.setSurename("");
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

	public void search() {
		this.logger.fine("Searching for Users");
		if ((this.name != null) && (this.surename != null)) {
			if (verifySearchField(this.name) && verifySearchField(this.surename)) {
				this.paginator.setEntities(this.userService.fullSearch(this.name, this.surename));
			}
		} else if ((this.name == null) && (this.surename != null)) {
			if (verifySearchField(this.surename)) {
				this.paginator.setEntities(this.userService.fullSearchBySurename(this.surename));
			}
		} else if ((this.name != null) && (this.surename == null)) {
			if (verifySearchField(this.name)) {
				this.paginator.setEntities(this.userService.fullSearchByName(this.name));
			}
		} else {
			this.paginator.setEntities(this.userService.findAll());
		}
		clearPopupFields();
	}

	public void select(final User manager) {
		this.manager = manager;
		this.rendered = false;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setLogger(final Logger logger) {
		this.logger = logger;
	}

	public void setManager(final User manager) {
		this.manager = manager;
	}

	public void setModal(final boolean modal) {
		this.modal = modal;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRendered(final boolean rendered) {
		this.rendered = rendered;
	}

	public void setSurename(final String surename) {
		this.surename = surename;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public void show() {
		this.rendered = true;
		search();
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
		final String userRoleName = userRole.getName();
		final String managerRoleName = managerRole.getName();
		final String msg = translate("user.error.role");
		if (userRoleName.equals("ADMIN") && !managerRoleName.equals("ADMIN")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		} else if (userRoleName.equals("RECRUITER_ADMIN")
				&& (managerRoleName.contains("TECHNIC") || managerRoleName.equals("RECRUITER"))) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		} else if (userRoleName.equals("TECHNIC_ADMIN")
				&& (managerRoleName.contains("RECRUITER") || managerRoleName.equals("TECHNIC"))) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		} else if (userRoleName.equals("TECHNIC") && managerRoleName.contains("RECRUITER")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		} else if (userRoleName.equals("RECRUITER") && managerRoleName.contains("TECHNIC")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	private boolean verifySearchField(final String text) {
		if (text.length() < 3) {
			addMessage(FacesMessage.SEVERITY_WARN, "error.search.camp_to_short", "error.search.camp_to_short");
			return false;
		}
		return true;
	}
}