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
public class UserViewBean extends AbstractBean {

	private static final long serialVersionUID = -2187385732087309689L;

	private User user;

	private String userId;

	@Inject
	private UserService userService;

	public String editUser() {
		this.flash.put("user", this.user);
		return "userEdit?faces-redirect=true";
	}

	private void error() {
		final NavigationHandler navigationHandler = this.facesContext.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(this.facesContext, null, "userSearch?faces-redirect=true");
		this.facesContext.renderResponse();
	}

	public User getUser() {
		return this.user;
	}

	public String getUserId() {
		return this.userId;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.userId != null) {
			try {
				final Long id = Long.parseLong(this.userId);
				this.user = this.userService.findById(id);
				if (this.user == null) {
					error();
				}
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	public String modifyUser() {
		this.flash.put("user", this.user);
		return "userEdit?faces-redirect=true";
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
