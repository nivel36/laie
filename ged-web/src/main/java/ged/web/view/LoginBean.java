package ged.web.view;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ged.web.core.view.AbstractPageBean;

@Named
@RequestScoped
public class LoginBean extends AbstractPageBean {

	private static final long serialVersionUID = 8364578958730650005L;

	private Locale locale;

	private transient String password;

	private String username;

	public Locale getLocale() {
		return this.locale;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	@PostConstruct
	public void init() {
		this.locale = this.facesContext.getApplication().getDefaultLocale();
	}

	public String login() {
		final ExternalContext externalContext = this.facesContext.getExternalContext();
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		try {
			request.login(this.username, this.password);
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

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}
