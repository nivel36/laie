package es.nivel36.laie.web.view;

import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.login.LoginService;

@Named
@ViewScoped
public class LoginView extends AbstractView {

	private static final long serialVersionUID = 4112471805164466458L;

	private static final Logger logger = LoggerFactory.getLogger(LoginView.class);

	public static final String URL = "/login.xhtml";

	private Locale locale;

	private transient String password;

	private String username;

	private transient @Inject LoginService loginService;

	private String forwardURL;

	@PostConstruct
	public void init() {
		logger.trace("Login init");
		final String remoteUser = this.externalContext.getRemoteUser();
		if (remoteUser != null) {
			logger.warn("User {} alredy logged", remoteUser);
			this.gotoIndex();
		}
		if (forwardURL == null) {
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			String requestURI = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
			String queryString = (String) request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);
			forwardURL = (queryString == null) ? requestURI : (requestURI + "?" + queryString);
		}

		this.setDefaultLocale();
	}

	public void login() {
		logger.debug("User {} login", this.username);
		if (this.username == null || this.password == null) {
			loginError();
		}
		final AuthenticationStatus status = this.loginService.login(this.username, this.password);
		if (AuthenticationStatus.SEND_FAILURE.equals(status)) {
			loginError();
		} else {
			String role;
			if (this.externalContext.isUserInRole("laie.admin")) {
				role = "laie.admin";
			} else {
				role = "laie.user";
			}
			this.sessionUser.load(this.username, role);
			if(forwardURL == null) {
				Faces.redirect(es.nivel36.laie.web.view.IndexView.URL);
			}
			Faces.redirect(forwardURL);
		}
	}

	private void loginError() {
		this.addMessage(FacesMessage.SEVERITY_ERROR, "auth.message.error", "auth.message.error");
		this.facesContext.validationFailed();
	}

	private void gotoIndex() {
		Faces.redirect(IndexView.URL);
	}

	private void setDefaultLocale() {
		this.locale = this.facesContext.getApplication().getDefaultLocale();
	}

	public Locale getLocale() {
		return this.locale;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
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

	public void setLoginService(final LoginService loginService) {
		Objects.requireNonNull(loginService);
		this.loginService = loginService;
	}
}