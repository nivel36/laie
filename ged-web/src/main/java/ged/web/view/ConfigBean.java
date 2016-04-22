package ged.web.view;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class ConfigBean extends AbstractPageBean {

	private static final long serialVersionUID = -2789492893353263506L;

	private User user;

	@Inject
	private UserService userService;

	public String cancel() {
		return null;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		this.user = this.sessionBean.getUser();
	}

	public void save() {
		this.user = this.userService.updateUser(this.user);
		this.sessionBean.setUser(this.user);
		this.sessionBean.setLocale(new Locale(this.user.getLanguage()));
		this.sessionBean.setRowsPerPage(this.user.getRowsPerPage());
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

}
