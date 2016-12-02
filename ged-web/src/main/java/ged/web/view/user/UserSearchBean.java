package ged.web.view.user;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Named
@ViewScoped
public class UserSearchBean extends AbstractUserSearchBean {

	private final transient static Logger logger = Logger.getLogger(UserSearchBean.class.getName());

	private static final long serialVersionUID = 2434819723782902618L;

	@Inject
	public UserSearchBean(final UserService userService) {
		super(userService);
	}

	public void deleteUser(final User user) {
		logger.log(Level.FINE, "Deleting an user");
		this.userService.delete(user);
		search();
	}

	public String editUser(final User user) {
		logger.log(Level.FINE, "Editing an user");
		this.flash.put("user", user);
		return "userEdit?faces-redirect=true";
	}

	public String newUser() {
		logger.log(Level.FINE, "Creating a new user");
		return "userEdit?faces-redirect=true";
	}
}