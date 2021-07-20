package es.nivel36.laie.login;

import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;

import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.user.User;
import es.nivel36.laie.user.UserJpaDao;


public abstract class AbstractIdentityStore {

	@Repository
	@Inject
	protected UserJpaDao userDao;

	public void setUserDao(final UserJpaDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}

	protected CredentialValidationResult validate(final User user) {
		if (user == null) {
			return NOT_VALIDATED_RESULT;
		}
		final Set<String> roles = this.getRoles(user);
		final LaieCallerPrincipal callerPrincipal = new LaieCallerPrincipal(user);
		return new CredentialValidationResult(callerPrincipal, roles);
	}
	
	private Set<String> getRoles(final User user) {
		final Set<String> roles = new HashSet<>();
		roles.add(user.getRole().toString());
		return roles;
	}
}
