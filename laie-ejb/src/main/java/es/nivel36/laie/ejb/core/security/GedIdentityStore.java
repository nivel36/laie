package es.nivel36.laie.ejb.core.security;

import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

import java.util.Objects;

import javax.persistence.NoResultException;
import javax.security.auth.login.LoginException;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import es.nivel36.laie.ejb.user.User;

public class GedIdentityStore extends AbstractIdentityStore implements IdentityStore {
	
	@Override
	public CredentialValidationResult validate(final Credential credential) {
		Objects.requireNonNull(credential);
		try {
			final User user;
			if (credential instanceof UsernamePasswordCredential) {
				user = this.findUserFromUsernamePasswordCredential(credential);
			} else if (credential instanceof CallerOnlyCredential) {
				user = this.findUserFromCallerOnlyCredential(credential);
			}
			else {
				return NOT_VALIDATED_RESULT;	
			}
			return this.validate(user);
		} catch (final LoginException e) {
			return NOT_VALIDATED_RESULT;
		}
	}

	private User findUserFromCallerOnlyCredential(final Credential credential) throws LoginException {
		try {
			final CallerOnlyCredential callerOnlyCredential = (CallerOnlyCredential) credential;
			final String email = callerOnlyCredential.getCaller();
			return this.userDao.findUserByEmail(email);
		}
		catch(NoResultException e) {
			throw new LoginException("Invalid email");
		}
	}

	private User findUserFromUsernamePasswordCredential(final Credential credential) throws LoginException {
		final UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
		final String email = usernamePasswordCredential.getCaller();
		final es.nivel36.laie.ejb.user.Credential laieCredential = this.userDao.findCredential(email);
		if (laieCredential == null) {
			throw new LoginException("Invalid email");
		}
		final String password = usernamePasswordCredential.getPasswordAsString();
		if (!laieCredential.isValid(password)) {
			throw new LoginException("Passwords doesn't match");
		}
		return laieCredential.getUser();
	}
}
