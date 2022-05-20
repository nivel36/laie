package es.nivel36.login;

import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

public abstract class AbstractIdentityStore {

	protected @Inject UserDao userDao;

	private Set<String> getRoles(final User user) {
		final Set<String> roles = new HashSet<>();
		roles.add(user.getRole().toString());
		return roles;
	}

	public CredentialValidationResult validate(final User user) {
		if (user == null) {
			return NOT_VALIDATED_RESULT;
		}
		final Set<String> roles = this.getRoles(user);
		final GedCallerPrincipal callerPrincipal = new GedCallerPrincipal(user);
		return new CredentialValidationResult(callerPrincipal.getName(), roles);
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}
