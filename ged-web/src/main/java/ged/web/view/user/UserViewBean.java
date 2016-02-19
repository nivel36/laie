package ged.web.view.user;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.user.User;
import ged.ejb.core.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class UserViewBean  extends AbstractBean {

	private static final long serialVersionUID = -2187385732087309689L;
	
	private User user;

	private String userId;

	@Inject
	private UserService userService;


	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (userId != null) {
			try {
				Long id = Long.parseLong(userId);
				user = userService.getByPrimaryKey(User.class, id);
				if (user == null) {
					error();
				}
			} catch (NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	private void error() {
		NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, null, "userSearch?faces-redirect=true");
		facesContext.renderResponse();
	}

	public User getUser() {
		return user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String editUser() {
		flash.put("user", user);
		return "userEdit?faces-redirect=true";
	}

	public String modifyUser() {
		flash.put("user", user);
		return "userEdit?faces-redirect=true";
	}
}
