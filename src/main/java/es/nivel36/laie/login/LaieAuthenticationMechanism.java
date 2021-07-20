package es.nivel36.laie.login;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AutoApplySession
@RememberMe(cookieSecureOnly = false, // Remove this when login is served over HTTPS.
		cookieMaxAgeSeconds = LaieAuthenticationMechanism.maxAge, //
		isRememberMeExpression = LaieAuthenticationMechanism.IS_REMEMBER_ME)
@LoginToContinue(loginPage = LaieAuthenticationMechanism.LOGIN_URL, errorPage = "", useForwardToLogin = false)
@RequestScoped
public class LaieAuthenticationMechanism implements HttpAuthenticationMechanism {

	static final int maxAge = 60 * 60 * 24 * 14;

	static final String IS_REMEMBER_ME = "#{self.isRememberMe(httpMessageContext)}";

	static final String LOGIN_URL = "/login.xhtml";

	@Inject
	private IdentityStore identityStore;

	@Override
	public AuthenticationStatus validateRequest(final HttpServletRequest request, final HttpServletResponse response,
			final HttpMessageContext httpMessageContext) throws AuthenticationException {
		final Credential credential = httpMessageContext.getAuthParameters().getCredential();
		if (credential != null) {
			final CredentialValidationResult validationResult = this.identityStore.validate(credential);
			if (validationResult.getStatus() == Status.VALID) {
				return httpMessageContext.notifyContainerAboutLogin(validationResult);
			} else {
				return httpMessageContext.responseUnauthorized();
			}
		} else {
			return httpMessageContext.doNothing();
		}
	}

	public Boolean isRememberMe(HttpMessageContext httpMessageContext) {
		return httpMessageContext.getRequest().getParameter("loginForm:rememberme_input") != null;
	}
}
