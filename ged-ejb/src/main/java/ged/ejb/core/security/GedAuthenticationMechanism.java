package ged.ejb.core.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(loginPage = "/login?continue=true", errorPage = "", useForwardToLogin = false))
@ApplicationScoped
public class GedAuthenticationMechanism implements HttpAuthenticationMechanism {

	@Inject
	private IdentityStore identityStore;

	@Override
	public AuthenticationStatus validateRequest(final HttpServletRequest request, final HttpServletResponse response,
			final HttpMessageContext httpMessageContext) throws AuthenticationException {
		final Credential credential = httpMessageContext.getAuthParameters().getCredential();

		if (credential != null) {
			return httpMessageContext.notifyContainerAboutLogin(identityStore.validate(credential));
		}
		else {
			return httpMessageContext.doNothing();
		}
	}
}
