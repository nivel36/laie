package ged.web.view.user;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.ejb.core.user.User;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserEditBean extends AbstractBean {

	private static final long serialVersionUID = 1923340646020120203L;
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
