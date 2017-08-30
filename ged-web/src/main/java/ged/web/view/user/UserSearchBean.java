package ged.web.view.user;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.user.User;

@Named
@ViewScoped
public class UserSearchBean extends AbstractUserSearchBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	public void deleteUser(final User user) {
		logger.debug( "Deleting an user");
		this.userService.delete(user);
		search();
	}

	public String editUser(final User user) {
		logger.debug( "Editing an user");
		this.flash.put("user", user);
		return "userEdit?faces-redirect=true";
	}

	public String newUser() {
		logger.debug( "Creating a new user");
		return "userEdit?faces-redirect=true";
	}
}