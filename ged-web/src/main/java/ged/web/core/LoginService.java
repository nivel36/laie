package ged.web.core;

import static javax.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import static org.omnifaces.util.Faces.getRequest;
import static org.omnifaces.util.Faces.getResponse;

import java.lang.invoke.MethodHandles;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.SessionUsers;
import ged.web.core.security.GedRememberMeIdentityStore;

@Stateless
public class LoginService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private transient SecurityContext securityContext;
	
	@Inject
	private transient GedRememberMeIdentityStore gedRememberMeIdentityStore;

	@Inject
	private transient FacesContext facesContext;

	@Inject
	private transient ExternalContext externalContext;

	@Inject
	private transient SessionUsers sessionUsers;

	public AuthenticationStatus login(String username, String password) {
		final UsernamePasswordCredential credential = new UsernamePasswordCredential(username, password);
		final AuthenticationParameters parameters = withParams().credential(credential).newAuthentication(true);
		final AuthenticationStatus authenticationStatus = this.authenticate(parameters);
		if (!authenticationStatus.equals(AuthenticationStatus.SEND_FAILURE)) {
			this.registerUserSession(username);
		} else {
			logger.error("Login error for username {}", username);
		}
		return authenticationStatus;
	}

	private void registerUserSession(String username) {
		this.facesContext.getExternalContext().getSessionMap().put("username", username);
		final String sessionId = this.externalContext.getSessionId(true);
		this.sessionUsers.login(username, sessionId);
	}

	private AuthenticationStatus authenticate(final AuthenticationParameters parameters) {
		final AuthenticationStatus status = this.securityContext.authenticate(getRequest(), getResponse(), parameters);
		if (status == SEND_CONTINUE) {
			// Prevent JSF from rendering a response so authentication mechanism can
			// continue.
			this.facesContext.responseComplete();
		}
		return status;
	}

	public void logout(String username) {
		final HttpSession session = (HttpSession) this.externalContext.getSession(true);
		final String sessionId = this.externalContext.getSessionId(false);
		sessionUsers.logout(username, sessionId);
		gedRememberMeIdentityStore.removeLoginToken(sessionId);
		session.invalidate();
	}
}