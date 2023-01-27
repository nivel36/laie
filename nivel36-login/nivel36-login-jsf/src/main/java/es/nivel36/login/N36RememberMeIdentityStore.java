package es.nivel36.login;

import java.util.Objects;
import java.util.Set;

import es.nivel36.login.LoginToken.TokenType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.RememberMeIdentityStore;
import jakarta.servlet.http.HttpServletRequest;

@ApplicationScoped
public class N36RememberMeIdentityStore extends AbstractIdentityStore implements RememberMeIdentityStore {
	
	private @Inject LoginTokenService loginTokenService;

	private @Inject HttpServletRequest request;
	
	@Override
	public CredentialValidationResult validate(final RememberMeCredential rememberMeCredential) {
		Objects.requireNonNull(rememberMeCredential);
		final String token = rememberMeCredential.getToken();
		final String tokenHash = CriptoUtil.digestPassword(token);
		final Account credential = accountService.findAccountByTokenHashAndType(tokenHash, TokenType.REMEMBER_ME);
		return this.validate(credential);
	}

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

	public void setLoginTokenService(final LoginTokenService loginTokenService) {
		Objects.requireNonNull(loginTokenService);
		this.loginTokenService = loginTokenService;
	}

	public void setRequest(final HttpServletRequest request) {
		Objects.requireNonNull(request);
		this.request = request;
	}
}
