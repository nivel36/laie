package es.nivel36.login;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.UUID.randomUUID;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import es.nivel36.login.LoginToken.TokenType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class LoginTokenService {

	private @PersistenceContext(unitName = "nivel36-login") EntityManager em;

	private @Inject AccountService accountService;

	public LoginToken findByTokenHash(final byte[] tokenHash) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(tokenHash);
		final String namedQuery = "LoginToken.findByTokenHash";
		final TypedQuery<LoginToken> query = em.createNamedQuery(namedQuery, LoginToken.class);
		query.setParameter("tokenHash", tokenHash);
		return query.getSingleResult();
	}

	public String generate(final String email, final String ipAddress, final String description,
			final TokenType tokenType) {
		final Instant expiration = now().plus(14, DAYS);
		return this.generate(email, ipAddress, description, tokenType, expiration);
	}

	public String generate(final String email, final String ipAddress, final String description,
			final TokenType tokenType, final Instant expiration) {
		final Account account = this.accountService.findAccount(email);
		if (account == null) {
			throw new IllegalStateException();
		}
		final String rawToken = randomUUID().toString();

		final LoginToken loginToken = new LoginToken();
		loginToken.setTokenHash(CriptoUtil.digestPassword(rawToken));
		loginToken.setExpiration(expiration);
		loginToken.setDescription(description);
		loginToken.setType(tokenType);
		loginToken.setIpAddress(ipAddress);
		loginToken.setAccount(account);
		final Instant created = Instant.now();
		loginToken.setCreated(created);
		loginToken.setExpiration(expiration);
		this.insert(loginToken);
		return rawToken;
	}

	public void remove(final String token) {
		Objects.requireNonNull(token);
		final byte[] tokenHash = CriptoUtil.digestPassword(token);
		final LoginToken loginToken = this.findByTokenHash(tokenHash);
		if (loginToken != null) {
			this.delete(loginToken);
		}
	}

	public void insert(final LoginToken loginToken) {
		Objects.requireNonNull(loginToken);
		this.em.persist(loginToken);
	}

	public void delete(final LoginToken loginToken) {
		Objects.requireNonNull(loginToken);
		final long id = loginToken.getId();
		final LoginToken reference = em.getReference(LoginToken.class, id);
		em.remove(reference);
	}

	public List<LoginToken> findExpiredTokens() {
		final String namedQuery = "LoginToken.findExpired";
		final TypedQuery<LoginToken> query = em.createNamedQuery(namedQuery, LoginToken.class);
		return query.getResultList();
	}

}
