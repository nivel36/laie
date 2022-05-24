package es.nivel36.login;

import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.identitystore.CredentialValidationResult;

public abstract class AbstractIdentityStore {

	protected @Inject AccountService accountService;

	public CredentialValidationResult validate(final Account account) {
		if (account == null) {
			return NOT_VALIDATED_RESULT;
		}
		final Set<String> roles = new HashSet<>();
		roles.add(account.getRole());
		final CallerPrincipal callerPrincipal = new CallerPrincipal(account.getUsername());
		return new CredentialValidationResult(callerPrincipal.getName(), roles);
	}

	public void setCredentialService(final AccountService accountService) {
		Objects.requireNonNull(accountService);
		this.accountService = accountService;
	}
}
