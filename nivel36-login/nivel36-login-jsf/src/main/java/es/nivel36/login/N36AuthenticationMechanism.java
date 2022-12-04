package es.nivel36.login;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.authentication.mechanism.http.RememberMe;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.CredentialValidationResult.Status;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@AutoApplySession
@RememberMe(cookieSecureOnly = false, // Remove this when login is served over HTTPS.
		cookieMaxAgeSeconds = 60 * 60 * 24 * 14, isRememberMeExpression = "#{self.isRememberMe(httpMessageContext)}")
@LoginToContinue(loginPage = N36AuthenticationMechanism.LOGIN_URL, errorPage = "", useForwardToLogin = true)
@RequestScoped
public class N36AuthenticationMechanism implements HttpAuthenticationMechanism {

	static final String LOGIN_URL = "/login.xhtml";

	private @Inject IdentityStore identityStore;

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
