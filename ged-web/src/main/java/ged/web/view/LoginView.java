package ged.web.view;

import static javax.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import static org.omnifaces.util.Faces.getRequest;
import static org.omnifaces.util.Faces.getResponse;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.SessionUsers;
import ged.web.core.util.Navigate;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@RequestScoped
public class LoginView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(LoginView.class);

	private static final long serialVersionUID = 1L;

	private Locale locale;

	private transient String password;

	@Inject
	private transient SecurityContext securityContext;

	@Inject
	private transient SessionUsers sessionUsers;

	private String username;

	private void authenticate(final AuthenticationParameters parameters) {
		final AuthenticationStatus status = this.securityContext.authenticate(getRequest(), getResponse(), parameters);
		if (status == SEND_FAILURE) {
			logger.warn("Authentication failed for user {}", this.username);
			this.addMessage(FacesMessage.SEVERITY_ERROR, "auth.message.error", "auth.message.error");
			this.facesContext.validationFailed();
		} else if (status == SEND_CONTINUE) {
			// Prevent JSF from rendering a response so authentication mechanism can
			// continue.
			this.facesContext.responseComplete();
		}
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

	private void gotoIndex() {
		Navigate.to(PageEnum.INDEX).doPost();
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
		final UsernamePasswordCredential credential = new UsernamePasswordCredential(this.username, this.password);
		final AuthenticationParameters parameters = withParams().credential(credential).newAuthentication(true);
		this.authenticate(parameters);
		registerUserSession();
		this.gotoIndex();
	}

	private void registerUserSession() {
		this.facesContext.getExternalContext().getSessionMap().put("username", username);
		final String sessionId = this.externalContext.getSessionId(true);
		this.sessionUsers.login(username, sessionId);
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