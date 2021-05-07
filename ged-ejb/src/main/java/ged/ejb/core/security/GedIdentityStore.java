package ged.ejb.core.security;

import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

import java.util.Objects;

import javax.security.auth.login.LoginException;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import ged.ejb.user.User;

public class GedIdentityStore extends AbstractIdentityStore implements IdentityStore {

	private User findUserFromCallerOnlyCredential(final Credential credential) {
		final CallerOnlyCredential callerOnlyCredential = (CallerOnlyCredential) credential;
		final String email = callerOnlyCredential.getCaller();
		return this.userDao.findUserByEmail(email);
	}

	private User findUserFromUsernamePasswordCredential(final Credential credential) throws LoginException {
		final UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
		final String email = usernamePasswordCredential.getCaller();
		final String password = usernamePasswordCredential.getPasswordAsString();
		final ged.ejb.user.Credential gedCredential = this.userDao.findCredential(email);
		if (gedCredential == null) {
			throw new LoginException("Invalid email");
		}
		if (!gedCredential.isValid(password)) {
			throw new LoginException("Passwords doesn't match");
		}
		return gedCredential.getUser();
	}

	@Override
	public CredentialValidationResult validate(final Credential credential) {
		Objects.requireNonNull(credential);
		try {
			if (credential instanceof UsernamePasswordCredential) {
				final User user = this.findUserFromUsernamePasswordCredential(credential);
				return this.validate(user);
			} else if (credential instanceof CallerOnlyCredential) {
				final User user = this.findUserFromCallerOnlyCredential(credential);
				return this.validate(user);
			}
		} catch (final LoginException e) {
			return NOT_VALIDATED_RESULT;
		}
		return NOT_VALIDATED_RESULT;
	}
}
