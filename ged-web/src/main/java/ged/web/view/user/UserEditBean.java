package ged.web.view.user;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.user.User;
import ged.ejb.core.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserEditBean extends AbstractBean {

	private static final long serialVersionUID = 1923340646020120203L;

	private List<User> managers;

	private User user;

	@Inject
	private UserService userService;

	public String cancel() {
		return "userSearch?faces-redirect=true";
	}

	public List<User> getManagers() {
		return this.managers;
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
		this.flash.put("user", this.user);
	}

	public void removeManager() {
		this.user.setManager(null);
	}

	public String save() {
		this.user = this.userService.insertOrUpdate(this.user);
		return "userSearch?faces-redirect=true";
	}

	public void setManagers(final List<User> managers) {
		this.managers = managers;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}