package es.nivel36.login;

import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;

import es.nivel36.login.LoginToken.TokenType;

@ApplicationScoped
public class N36RememberMeIdentityStore extends AbstractIdentityStore implements RememberMeIdentityStore {
	
	private @Inject N36IdentityStore n36IdentityStore;

	private @Inject LoginTokenService loginTokenService;

	private @Inject HttpServletRequest request;
	
	private @Inject AccountService accountService;

	@Override
	public String generateLoginToken(final CallerPrincipal callerPrincipal, final Set<String> groups) {
		Objects.requireNonNull(callerPrincipal);
		final String ipAddress = request.getRemoteAddr();
		final String description = String.format("Remember me session for %s on %s", ipAddress,
				request.getHeader("User-Agent"));
		final String name = callerPrincipal.getName();
		return loginTokenService.generate(name, ipAddress, description, TokenType.REMEMBER_ME);
	}

	@Override
	public void removeLoginToken(final String loginToken) {
		Objects.requireNonNull(loginToken);
		loginTokenService.remove(loginToken);
	}

	public void setdIdentityStore(final N36IdentityStore n36IdentityStore) {
		Objects.requireNonNull(n36IdentityStore);
		this.n36IdentityStore = n36IdentityStore;
	}

	public void setLoginTokenService(final LoginTokenService loginTokenService) {
		Objects.requireNonNull(loginTokenService);
		this.loginTokenService = loginTokenService;
	}

	public void setRequest(final HttpServletRequest request) {
		Objects.requireNonNull(request);
		this.request = request;
	}

	@Override
	public CredentialValidationResult validate(final RememberMeCredential rememberMeCredential) {
		Objects.requireNonNull(rememberMeCredential);
		final String token = rememberMeCredential.getToken();
		final byte[] tokenHash = CriptoUtil.digestPassword(token);
		final Account credential = accountService.findUserByTokenHashAndType(tokenHash, TokenType.REMEMBER_ME);
		return n36IdentityStore.validate(credential);
	}
}
