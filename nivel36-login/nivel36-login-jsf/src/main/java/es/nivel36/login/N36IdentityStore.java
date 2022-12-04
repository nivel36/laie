package es.nivel36.login;

import java.util.Objects;

import javax.security.auth.login.LoginException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.CallerOnlyCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class N36IdentityStore extends AbstractIdentityStore implements IdentityStore {

	@Override
	public CredentialValidationResult validate(final Credential credential) {
		Objects.requireNonNull(credential);
		try {
			final Account account;
			if (credential instanceof UsernamePasswordCredential) {
				account = this.findUserFromUsernamePasswordCredential(credential);
			} else if (credential instanceof CallerOnlyCredential) {
				account = this.findUserFromCallerOnlyCredential(credential);
			} else {
				return CredentialValidationResult.NOT_VALIDATED_RESULT;
			}
			return this.validate(account);
		} catch (final LoginException e) {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
		}
	}

	private Account findUserFromCallerOnlyCredential(final Credential credential) throws LoginException {
		final CallerOnlyCredential callerOnlyCredential = (CallerOnlyCredential) credential;
		final String email = callerOnlyCredential.getCaller();
		final Account account = this.accountService.findAccount(email);
		if (account == null) {
			throw new LoginException("Invalid email");
		}
		return account;
	}

	private Account findUserFromUsernamePasswordCredential(final Credential credential) throws LoginException {
		final UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
		final String email = usernamePasswordCredential.getCaller();
		final Account account = this.accountService.findAccount(email);
		if (account == null) {
			throw new LoginException("Invalid email");
		}
		final String password = usernamePasswordCredential.getPasswordAsString();
		if (!account.isValid(password)) {
			throw new LoginException("Passwords doesn't match");
		}
		return account;
	}
}
