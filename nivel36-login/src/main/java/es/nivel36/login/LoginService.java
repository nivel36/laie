package es.nivel36.login;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	private @Inject ExternalContext externalContext;

	private @Inject FacesContext facesContext;

	private @Inject RememberMeIdentityStore rememberMeIdentityStore;

	private @Inject SecurityContext securityContext;

	private AuthenticationStatus authenticate(final AuthenticationParameters parameters) {
		final HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		final AuthenticationStatus status = this.securityContext.authenticate(request, response, parameters);
		if (status == AuthenticationStatus.SEND_CONTINUE) {
			// Prevent JSF from rendering a response so authentication mechanism can
			// continue.
			this.facesContext.responseComplete();
		}
		return status;
	}

	private void invalidateSession() {
		final HttpSession session = (HttpSession) this.externalContext.getSession(false);
		session.invalidate();
	}

	public AuthenticationStatus login(final String username, final String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		final UsernamePasswordCredential credential = new UsernamePasswordCredential(username, password);
		final AuthenticationParameters parameters = withParams().credential(credential).newAuthentication(true);
		final AuthenticationStatus authenticationStatus = this.authenticate(parameters);
		if (authenticationStatus == AuthenticationStatus.SEND_FAILURE) {
			logger.error("Login error for username {}", username);
		} else {
			logger.info("User {} login", username);
		}
		return authenticationStatus;
	}

	public void logout(final String username) {
		Objects.requireNonNull(username);
		logger.info("User {} logout", username);
		try {
			this.removeRememberMeCookie();
			this.invalidateSession();
			((HttpServletRequest) this.externalContext.getRequest()).logout();
		} catch (final ServletException e) {
			logger.error("Error in the users {} logout", username);
			logger.error("Error trace: ", e);
		}
	}

	private void removeLoginToken(final Cookie cookie) {
		final String tokenHash = cookie.getValue();
		this.rememberMeIdentityStore.removeLoginToken(tokenHash);
		cookie.setMaxAge(0);
	}

	private void removeRememberMeCookie() {
		final HttpServletRequest httpResuqest = (HttpServletRequest) this.externalContext.getRequest();
		final Cookie[] cookies = httpResuqest.getCookies();
		for (final Cookie cookie : cookies) {
			if (cookie.getName().equals("JREMEMBERMEID")) {
				this.removeLoginToken(cookie);
				break;
			}
		}
	}
}