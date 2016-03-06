package ged.web.view;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractBean;

@Named
@RequestScoped
public class LoginBean extends AbstractBean {

	private static final long serialVersionUID = 8364578958730650005L;

	private transient String password;

	private String username;

	@Inject
	private UserService userService;

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		final String user = this.facesContext.getExternalContext().getRemoteUser();
		return user;
	}

	public boolean isUserLoggedIn() {
		final String user = getUsername();
		final boolean result = !((user == null) || user.isEmpty());
		return result;
	}

	public String login() {
		final HttpServletRequest request = (HttpServletRequest) this.facesContext.getExternalContext().getRequest();
		try {
			request.login(this.username, this.password);
			final User user = this.userService.findUserByUsername(this.username);
			this.sessionBean.setUser(user);
			return "/faces/index?faces-redirect=true";
		} catch (final ServletException e) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown login", null));
			return null;
		}
	}

	public String logout() {
		final HttpSession session = (HttpSession) this.facesContext.getExternalContext().getSession(true);
		session.invalidate();
		return "/login?faces-redirect=true";
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
