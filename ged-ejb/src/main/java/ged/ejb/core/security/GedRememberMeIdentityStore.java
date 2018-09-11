package ged.ejb.core.security;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;

@ApplicationScoped
public class GedRememberMeIdentityStore implements RememberMeIdentityStore {

	@Override
	public String generateLoginToken(final CallerPrincipal callerPrincipal, final Set<String> groups) {
		return null;
	}

	@Override
	public void removeLoginToken(final String token) {
	}

	@Override
	public CredentialValidationResult validate(final RememberMeCredential credential) {
		return CredentialValidationResult.INVALID_RESULT;
	}
}
