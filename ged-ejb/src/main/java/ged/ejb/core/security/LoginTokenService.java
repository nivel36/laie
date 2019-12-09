package ged.ejb.core.security;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.UUID.randomUUID;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.model.Repository;
import ged.ejb.core.security.LoginToken.TokenType;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Stateless
public class LoginTokenService {

	@Inject
	@Repository
	private LoginTokenDao loginTokenDao;

	@Inject
	private UserService userService;

	public LoginToken findByTokenHash(final String tokenHash) {
		Objects.requireNonNull(tokenHash);
		return this.loginTokenDao.findByTokenHash(tokenHash);
	}

	public String generate(final String email, final String ipAddress, final String description,
			final TokenType tokenType) {
		final Instant expiration = now().plus(14, DAYS);
		return generate(email, ipAddress, description, tokenType, expiration);
	}

	public String generate(final String email, final String ipAddress, final String description,
			final TokenType tokenType, final Instant expiration) {
		final User user = userService.findUserByEmail(email);
		if (user == null) {
			throw new IllegalStateException();
		}
		final String rawToken = randomUUID().toString();

		final LoginToken loginToken = new LoginToken();
		loginToken.setTokenHash(CriptoUtil.digestPassword(rawToken));
		loginToken.setExpiration(expiration);
		loginToken.setDescription(description);
		loginToken.setType(tokenType);
		loginToken.setIpAddress(ipAddress);
		loginToken.setUser(user);
		user.getLoginTokens().add(loginToken);
		return rawToken;
	}

	public void remove(final String token) {
		Objects.requireNonNull(token);
		final String tokenHash = CriptoUtil.digestPassword(token).toString();
		final LoginToken loginToken = this.findByTokenHash(tokenHash);
		this.loginTokenDao.delete(loginToken);
	}

	public void removeExpiredTokens() {
		final List<LoginToken> loginTokens = this.loginTokenDao.findExpiredTokens();
		for (final LoginToken loginToken : loginTokens) {
			this.loginTokenDao.delete(loginToken);
		}
	}

	public void setLoginTokenDao(LoginTokenDao loginTokenDao) {
		Objects.requireNonNull(loginTokenDao);
		this.loginTokenDao = loginTokenDao;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

}
