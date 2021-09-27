package es.nivel36.laie.ejb.core.security;

import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;

import es.nivel36.laie.ejb.core.security.LoginToken.TokenType;
import es.nivel36.laie.ejb.user.User;

@ApplicationScoped
public class GedRememberMeIdentityStore extends AbstractIdentityStore implements RememberMeIdentityStore {

	@Inject
	private GedIdentityStore gedIdentityStore;

	@Inject
	private LoginTokenService loginTokenService;

	@Inject
	private HttpServletRequest request;

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

	public void setGedIdentityStore(final GedIdentityStore gedIdentityStore) {
		Objects.requireNonNull(gedIdentityStore);
		this.gedIdentityStore = gedIdentityStore;
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
	public CredentialValidationResult validate(final RememberMeCredential credential) {
		Objects.requireNonNull(credential);
		final String token = credential.getToken();
		final byte[] tokenHash = CriptoUtil.digestPassword(token);
		final User user = userDao.findUserByTokenHashAndType(tokenHash, TokenType.REMEMBER_ME);
		return gedIdentityStore.validate(user);
	}
}
