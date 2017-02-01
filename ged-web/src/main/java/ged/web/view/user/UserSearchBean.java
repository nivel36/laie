package ged.web.view.user;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Named
@ViewScoped
public class UserSearchBean extends AbstractUserSearchBean {

	private final transient static Logger logger = LoggerFactory.getLogger(UserSearchBean.class.getName());

	private static final long serialVersionUID = 2434819723782902618L;

	@Inject
	public UserSearchBean(final UserService userService) {
		super(userService);
	}

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