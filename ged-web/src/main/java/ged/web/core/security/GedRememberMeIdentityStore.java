package ged.web.core.security;

import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;

import ged.ejb.core.security.CriptoUtil;
import ged.ejb.core.security.GedIdentityStore;
import ged.ejb.core.security.LoginToken.TokenType;
import ged.ejb.core.security.LoginTokenService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@ApplicationScoped
public class GedRememberMeIdentityStore implements RememberMeIdentityStore {

	@Inject
	private GedIdentityStore gedIdentityStore;

	@Inject
	private LoginTokenService loginTokenService;

	@Inject
	private HttpServletRequest request;

	@Inject
	private UserService userService;

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

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	@Override
	public CredentialValidationResult validate(final RememberMeCredential credential) {
		Objects.requireNonNull(credential);
		final String token = credential.getToken();
		final byte[] tokenHash = CriptoUtil.digestPassword(token);
		final User user = userService.findUserByTokenHash(tokenHash, TokenType.REMEMBER_ME);
		return gedIdentityStore.validate(user);
	}
}
