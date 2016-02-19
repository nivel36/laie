package ged.web.view.user;

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
	
	private User user;

	@Inject
	private UserService userService;

	public String cancel() {
		return "userSearch?faces-redirect=true";
	}

	public User getUser() {
		return user;
	}

	@PostConstruct
	private void init() {
		if (flash.containsKey("user")) {
			user = (User) flash.get("user");
		} else {
			user = new User();
		}
		flash.put("user", user);
	}

	public String save() {
		user = userService.insertOrUpdate(user);
		return "userSearch?faces-redirect=true";
	}

	public void setUser(User user) {
		this.user = user;
	}
}