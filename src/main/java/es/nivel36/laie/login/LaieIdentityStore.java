package es.nivel36.laie.login;

import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

import java.util.Objects;

import javax.security.auth.login.LoginException;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import es.nivel36.laie.user.User;

public class LaieIdentityStore extends AbstractIdentityStore implements IdentityStore {

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
	
	private User findUserFromCallerOnlyCredential(final Credential credential) {
		final CallerOnlyCredential callerOnlyCredential = (CallerOnlyCredential) credential;
		final String email = callerOnlyCredential.getCaller();
		return this.userDao.findUserByEmail(email);
	}

	private User findUserFromUsernamePasswordCredential(final Credential credential) throws LoginException {
		final UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
		final String email = usernamePasswordCredential.getCaller();
		final String password = usernamePasswordCredential.getPasswordAsString();
		final LaieCredential laieCredential = this.userDao.findCredential(email);
		if (laieCredential == null) {
			throw new LoginException("Invalid email");
		}
		if (!laieCredential.isValid(password)) {
			throw new LoginException("Passwords doesn't match");
		}
		return laieCredential.getUser();
	}
}
