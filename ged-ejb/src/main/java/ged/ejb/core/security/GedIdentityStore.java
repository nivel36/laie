package ged.ejb.core.security;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import ged.ejb.core.LoginService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

public class GedIdentityStore implements IdentityStore {

	@Inject
	private LoginService loginService;

	@Inject
	private UserService userService;

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	@Override
	public CredentialValidationResult validate(final Credential credential) {
		Supplier<User> userSupplier = null;

		if (credential instanceof UsernamePasswordCredential) {
			final String email = ((UsernamePasswordCredential) credential).getCaller();
			final String password = ((UsernamePasswordCredential) credential).getPasswordAsString();
			userSupplier = () -> loginService.login(email, password);
		}
		else if (credential instanceof CallerOnlyCredential) {
			final String email = ((CallerOnlyCredential) credential).getCaller();
			userSupplier = () -> userService.findUserByEmail(email);
		}
		else {
			throw new SecurityException();
		}

		final User user = userSupplier.get();
		final Set<String> roles = new HashSet<>();
		roles.add(user.getRole().toString());
		return new CredentialValidationResult(new GedCallerPrincipal(user), roles);
	}
}
