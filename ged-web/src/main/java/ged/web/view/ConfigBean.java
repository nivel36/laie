package ged.web.view;

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
public class ConfigBean extends AbstractBean {

	private static final long serialVersionUID = -2789492893353263506L;

	private User user;
	
	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		user = sessionBean.getUser();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void save() {
		user = userService.update(user);
		sessionBean.setUser(user);
		sessionBean.setLocale(new Locale(user.getLanguage()));
		sessionBean.setRowsPerPage(user.getRowsPerPage());
	}

	public String cancel() {
		return null;
	}

}
