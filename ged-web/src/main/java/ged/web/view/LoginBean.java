package ged.web.view;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ged.web.core.view.AbstractBean;

@Named
@RequestScoped
public class LoginBean extends AbstractBean {

	private static final long serialVersionUID = 8364578958730650005L;

	private String username;

	private transient String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String login() {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		try {
			request.login(username, password);
			return "/faces/index?faces-redirect=true";
		} catch (ServletException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown login", null));
			return null;
		}
	}

	public String logout() {
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.invalidate();
		return "/login?faces-redirect=true";
	}

	public boolean isUserLoggedIn() {
		String user = this.getUsername();
		boolean result = !((user == null) || user.isEmpty());
		return result;
	}

	public String getUsername() {
		String user = facesContext.getExternalContext().getRemoteUser();
		return user;
	}
}
