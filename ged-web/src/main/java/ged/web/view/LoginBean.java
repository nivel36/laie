package ged.web.view;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
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
	private transient UserService userService;

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public String login() {
		final ExternalContext externalContext = this.facesContext.getExternalContext();
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		try {
			request.login(this.username, this.password);
			final User user = this.userService.findUserByUsername(this.username);
			this.sessionBean.setUser(user);
			return "/faces/index?faces-redirect=true";
		} catch (final ServletException e) {
			final String message = translate("login.error.unknow_login");
			final FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
			this.facesContext.addMessage(null, facesMessage);
			return null;
		}
	}

	public String logout() {
		final ExternalContext externalContext = this.facesContext.getExternalContext();
		final HttpSession session = (HttpSession) externalContext.getSession(true);
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
