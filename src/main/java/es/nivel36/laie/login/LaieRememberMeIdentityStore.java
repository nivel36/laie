package es.nivel36.laie.login;

import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import es.nivel36.laie.login.token.LoginTokenService;
import es.nivel36.laie.login.token.LoginToken.TokenType;
import es.nivel36.laie.user.User;

@ApplicationScoped
public class LaieRememberMeIdentityStore extends AbstractIdentityStore implements RememberMeIdentityStore {

	@Inject
	private LaieIdentityStore laieIdentityStore;

	@Inject
	private LoginTokenService loginTokenService;

	@Inject
	private HttpServletRequest request;

	@Override
	public String generateLoginToken(final CallerPrincipal callerPrincipal, final Set<String> groups) {
		Objects.requireNonNull(callerPrincipal);
		final String ipAddress = request.getRemoteAddr();
		final String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
		final String description = String.format("Remember me maxAge for %s on %s", ipAddress, userAgent);
		final String name = callerPrincipal.getName();
		return loginTokenService.generate(name, ipAddress, description, TokenType.REMEMBER_ME);
	}

	@Override
	public void removeLoginToken(final String loginToken) {
		Objects.requireNonNull(loginToken);
		loginTokenService.remove(loginToken);
	}

	public void setLaieIdentityStore(final LaieIdentityStore laieIdentityStore) {
		Objects.requireNonNull(laieIdentityStore);
		this.laieIdentityStore = laieIdentityStore;
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
		final byte[] tokenHash = digestPassword(token);
		final User user = userDao.findUserByTokenHashAndType(tokenHash, TokenType.REMEMBER_ME);
		return laieIdentityStore.validate(user);
	}

	private byte[] digestPassword(String token) {
		MessageDigester md = new MessageDigester(token);
		return md.digest();
	}
}
