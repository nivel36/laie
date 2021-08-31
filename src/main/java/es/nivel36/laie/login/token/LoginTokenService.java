package es.nivel36.laie.login.token;

import static java.util.UUID.randomUUID;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.login.LaieCredential;
import es.nivel36.laie.login.MessageDigester;
import es.nivel36.laie.login.token.LoginToken.TokenType;
import es.nivel36.laie.user.UserJpaDao;

@Stateless
public class LoginTokenService {

	@Inject
	@Repository
	private LoginTokenDao loginTokenDao;

	@Inject
	@Repository
	private UserJpaDao userDao;

	private LoginToken buildLoginToken(final String ipAddress, final String description, final TokenType tokenType,
			final LaieCredential credential, final String rawToken) {
		final LoginToken loginToken = new LoginToken();
		loginToken.setTokenHash(this.digestPassword(rawToken));
		final Instant now = Instant.now();
		loginToken.setCreated(now);
		if (tokenType.equals(TokenType.REMEMBER_ME)) {
			loginToken.setExpiration(now.plus(30, ChronoUnit.DAYS));
		} else {
			loginToken.setExpiration(now.plus(30, ChronoUnit.MINUTES));
		}
		loginToken.setDescription(description);
		loginToken.setType(tokenType);
		loginToken.setIpAddress(ipAddress);
		loginToken.setUser(credential.getUser());
		return loginToken;
	}

	private byte[] digestPassword(final String token) {
		final MessageDigester md = new MessageDigester(token);
		return md.digest();
	}

	public LoginToken findByTokenHash(final byte[] tokenHash) {
		Objects.requireNonNull(tokenHash);
		return this.loginTokenDao.findByTokenHash(tokenHash);
	}

	public String generate(final String email, final String ipAddress, final String description,
			final TokenType tokenType) {
		final LaieCredential credential = this.userDao.findCredential(email);
		if (credential == null) {
			throw new SecurityException();
		}
		final String rawToken = randomUUID().toString();
		final LoginToken loginToken = this.buildLoginToken(ipAddress, description, tokenType, credential, rawToken);
		this.loginTokenDao.insert(loginToken);
		return rawToken;
	}

	public void remove(final String token) {
		Objects.requireNonNull(token);
		final byte[] tokenHash = this.digestPassword(token);
		final LoginToken loginToken = this.findByTokenHash(tokenHash);
		if (loginToken != null) {
			this.loginTokenDao.delete(loginToken);
		}
	}

	public void setLoginTokenDao(final LoginTokenDao loginTokenDao) {
		Objects.requireNonNull(loginTokenDao);
		this.loginTokenDao = loginTokenDao;
	}

	public void setUserService(final UserJpaDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}
