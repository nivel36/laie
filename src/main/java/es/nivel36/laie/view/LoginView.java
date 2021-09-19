package es.nivel36.laie.view;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.view.AbstractView;
import es.nivel36.laie.login.LoginService;


@Named
@ViewScoped
public class LoginView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(LoginView.class);

	private static final long serialVersionUID = 1L;

	private Locale locale;

	@Inject
	private transient LoginService loginService;

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

	private void gotoIndex() {
		final NavigationHandler nav = this.facesContext.getApplication().getNavigationHandler();
		nav.handleNavigation(this.facesContext, null, "/index?faces-redirect=true");
		this.facesContext.renderResponse(); 
	}

	@PostConstruct
	public void init() {
		logger.trace("Login init");
		final String remoteUser = this.externalContext.getRemoteUser();
		if (remoteUser != null) {
			logger.warn("User {} alredy logged", remoteUser);
			this.gotoIndex();
		}
		this.setDefaultLocale();
	}

	public void login() {
		logger.debug("User {} login", this.username);
		final AuthenticationStatus status = this.loginService.login(this.username, this.password);
		if (status.equals(AuthenticationStatus.SEND_FAILURE)) {
			this.addMessage(FacesMessage.SEVERITY_ERROR, "auth.message.error", "auth.message.error");
			this.facesContext.validationFailed();
		} else {
			this.sessionUser.load(this.username);
			this.gotoIndex();
		}
	}

	private void setDefaultLocale() {
		this.locale = this.facesContext.getApplication().getDefaultLocale();
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