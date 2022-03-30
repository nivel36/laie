package es.nivel36.laie.ejb.core.security;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.UUID.randomUUID;

import java.time.Instant;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.core.model.Repository;
import es.nivel36.laie.ejb.core.security.LoginToken.TokenType;
import es.nivel36.laie.ejb.user.Credential;
import es.nivel36.laie.ejb.user.UserService;

@Stateless
public class LoginTokenService {

	@Inject
	@Repository
	private LoginTokenDao loginTokenDao;

	@Inject
	private UserService userService;

	public LoginToken findByTokenHash(final byte[] tokenHash) {
		Objects.requireNonNull(tokenHash);
		return this.loginTokenDao.findByTokenHash(tokenHash);
	}

	public String generate(final String email, final String ipAddress, final String description,
			final TokenType tokenType) {
		final Instant expiration = now().plus(14, DAYS);
		return this.generate(email, ipAddress, description, tokenType, expiration);
	}

	public String generate(final String email, final String ipAddress, final String description,
			final TokenType tokenType, final Instant expiration) {
		final Credential credential = this.userService.findCredential(email);
		if (credential == null) {
			throw new IllegalStateException();
		}
		final String rawToken = randomUUID().toString();

		final LoginToken loginToken = new LoginToken();
		loginToken.setTokenHash(CriptoUtil.digestPassword(rawToken));
		loginToken.setExpiration(expiration);
		loginToken.setDescription(description);
		loginToken.setType(tokenType);
		loginToken.setIpAddress(ipAddress);
		loginToken.setUser(credential.getUser());
		this.loginTokenDao.insert(loginToken);
		return rawToken;
	}

	public void remove(final String token) {
		Objects.requireNonNull(token);
		final byte[] tokenHash = CriptoUtil.digestPassword(token);
		final LoginToken loginToken = this.findByTokenHash(tokenHash);
		if (loginToken != null) {
			this.loginTokenDao.delete(loginToken);
		}
	}

	public void setLoginTokenDao(final LoginTokenDao loginTokenDao) {
		Objects.requireNonNull(loginTokenDao);
		this.loginTokenDao = loginTokenDao;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}
