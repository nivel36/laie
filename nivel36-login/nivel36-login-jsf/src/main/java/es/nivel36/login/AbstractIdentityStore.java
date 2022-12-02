package es.nivel36.login;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;

public abstract class AbstractIdentityStore {

	protected @Inject AccountService accountService;

	public CredentialValidationResult validate(final Account account) {
		if (account == null) {
			return CredentialValidationResult.NOT_VALIDATED_RESULT;
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
