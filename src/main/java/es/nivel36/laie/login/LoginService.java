package es.nivel36.laie.login;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import static org.omnifaces.util.Faces.getRequest;
import static org.omnifaces.util.Faces.getResponse;

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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.user.UserJpaDao;


@Stateless
public class LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Inject
	private ExternalContext externalContext;

	@Inject
	private FacesContext facesContext;

	@Inject
	private RememberMeIdentityStore rememberMeIdentityStore;

	@Inject
	private SecurityContext securityContext;

	@Inject
	@Repository
	private UserJpaDao userDao;

	private void addSessionUser(final String username) {
		this.facesContext.getExternalContext().getSessionMap().put("username", username);
	}

	private AuthenticationStatus authenticate(final AuthenticationParameters parameters) {
		final AuthenticationStatus status = this.securityContext.authenticate(getRequest(), getResponse(), parameters);
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
		final UsernamePasswordCredential credential = new UsernamePasswordCredential(username, password);
		final AuthenticationParameters parameters = withParams().credential(credential).newAuthentication(true);
		final AuthenticationStatus authenticationStatus = this.authenticate(parameters);
		if (authenticationStatus == AuthenticationStatus.SEND_FAILURE) {
			logger.error("Login error for username {}", username);
		} else {
			logger.info("User {} login", username);
			this.addSessionUser(username);
		}
		return authenticationStatus;
	}

	public void logout(final String username) {
		Objects.requireNonNull(username);
		logger.info("User {} logout", username);
		this.removeRememberMeCookie();
		this.invalidateSession();
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