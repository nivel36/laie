package ged.ejb.core.security;

import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import ged.ejb.user.User;
import ged.ejb.user.UserService;

public class GedIdentityStore implements IdentityStore {

	@Inject
	private UserService userService;

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	@Override
	public CredentialValidationResult validate(final Credential credential) {
		Objects.requireNonNull(credential);
		final User user;
		if (credential instanceof UsernamePasswordCredential) {
			try {
				final String email = ((UsernamePasswordCredential) credential).getCaller();
				final String password = ((UsernamePasswordCredential) credential).getPasswordAsString();
				user = this.userService.login(email, password);
			} catch (final LoginException e) {
				return NOT_VALIDATED_RESULT;
			}
		} else if (credential instanceof CallerOnlyCredential) {
			final String email = ((CallerOnlyCredential) credential).getCaller();
			user = this.userService.findUserByEmail(email);
		} else {
			return NOT_VALIDATED_RESULT;
		}

		return validate(user);
	}

	public CredentialValidationResult validate(final User user) {
		if (user == null) {
			return NOT_VALIDATED_RESULT;
		}
		final Set<String> roles = getRoles(user);
		return new CredentialValidationResult(new GedCallerPrincipal(user), roles);
	}

	private Set<String> getRoles(final User user) {
		final Set<String> roles = new HashSet<>();
		roles.add(user.getRole().toString());
		return roles;
	}
}
