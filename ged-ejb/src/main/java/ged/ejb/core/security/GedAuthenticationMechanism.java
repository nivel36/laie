package ged.ejb.core.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class GedAuthenticationMechanism implements HttpAuthenticationMechanism {

	@Inject
	private IdentityStore identityStore;

	@Override
	public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response,
			HttpMessageContext httpMessageContext) throws AuthenticationException {
		Credential credential = httpMessageContext.getAuthParameters().getCredential();

		if (credential != null) {
			return httpMessageContext.notifyContainerAboutLogin(identityStore.validate(credential));
		} else {
			return httpMessageContext.doNothing();
		}
	}
}
