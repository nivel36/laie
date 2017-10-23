package ged.web.view;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ged.ejb.core.LoginService;
import ged.web.core.util.TransaltionUtils;
import ged.web.core.view.AbstractPageBean;

@Named
@RequestScoped
public class LoginBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8364578958730650005L;
	
	private Locale locale;

	@Inject
	private LoginService loginService;

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
		logger.debug("Login Bean init");
		this.locale = this.facesContext.getApplication().getDefaultLocale();
	}

	public String login() {
		logger.debug("Username {} login", this.username);
		final ExternalContext externalContext = this.facesContext.getExternalContext();
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		try {
			request.login(this.username, this.password);
			loginService.login(this.username);
			return "/faces/index?faces-redirect=true";
		} catch (final ServletException e) {
			logger.warn("Bad login credentials", e);
			final String message = TransaltionUtils.translate("login.error.unknow_login");
			final FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
			this.facesContext.addMessage(null, facesMessage);
			return null;
		}
	}

	public String logout() {
		logger.debug("Username {} logout", this.username);
		final ExternalContext externalContext = this.facesContext.getExternalContext();
		final HttpSession session = (HttpSession) externalContext.getSession(true);
		session.invalidate();
		return "/login?faces-redirect=true";
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
}