package ged.web.core.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AutoApplySession // For "Is user already logged-in?"
@RememberMe(cookieSecureOnly = false, // Remove this when login is served over HTTPS.
		cookieMaxAgeSeconds = 60 * 60 * 24 * 14) // 14 days.
@LoginToContinue(loginPage = GedAuthenticationMechanism.LOGIN_URL, errorPage = "", useForwardToLogin = false)
@ApplicationScoped
public class GedAuthenticationMechanism implements HttpAuthenticationMechanism {

	static final String LOGIN_URL = "/login.xhtml";

	@Inject
	private IdentityStore identityStore;

	@Override
	public AuthenticationStatus validateRequest(final HttpServletRequest request, final HttpServletResponse response,
			final HttpMessageContext httpMessageContext) throws AuthenticationException {
		final Credential credential = httpMessageContext.getAuthParameters().getCredential();

		if (credential != null) {
			return httpMessageContext.notifyContainerAboutLogin(this.identityStore.validate(credential));
		} else {
			return httpMessageContext.doNothing();
		}
	}
}
