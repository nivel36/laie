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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.web.core.util.Navigate;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

@Named
@RequestScoped
public class LoginBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(LoginBean.class);

	private static final long serialVersionUID = 8364578958730650005L;

	private Locale locale;

	private transient String password;

	@Inject
	private SecurityContext securityContext;

	private String username;

	private void authenticate(final AuthenticationParameters parameters) {
		final AuthenticationStatus status = this.securityContext.authenticate(getRequest(), getResponse(), parameters);
		if (status == SEND_FAILURE) {
			logger.warn("Authentication failed for username {}", this.username);
			addMessage(FacesMessage.SEVERITY_ERROR, "auth.message.error.failure", null);
			facesContext.validationFailed();
		} else if (status == SEND_CONTINUE) {
			// Prevent JSF from rendering a response so authentication mechanism can
			// continue.
			facesContext.responseComplete();
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
		final String username = this.externalContext.getRemoteUser();
		if (username != null) {
			logger.warn("User {} alredy logged", username);
			gotoIndex();
		}
		setDefaultLocale();
	}

	public void login() {
		logger.debug("User {} login", this.username);
		final UsernamePasswordCredential credential = new UsernamePasswordCredential(this.username, this.password);
		final AuthenticationParameters parameters = withParams().credential(credential).newAuthentication(true);
		authenticate(parameters);
		gotoIndex();
	}

	public void logout() {
		logger.debug("User {} logout", this.username);
		invalidateSession();
	}

	private void invalidateSession() {
		final HttpSession session = (HttpSession) externalContext.getSession(true);
		session.invalidate();
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