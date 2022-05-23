package es.nivel36.login;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.login.LoginToken.TokenType;

@Stateless
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	private @PersistenceContext(unitName = "nivel36-login") EntityManager em;

	public Account findAccountByTokenHashAndType(final byte[] tokenHash, final TokenType type) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(type);
		logger.debug("Find account by tokenHash {} and type {}", tokenHash, type);
		try {
			final String namedQuery = "Account.findByTokenHashAndType";
			final TypedQuery<Account> query = em.createNamedQuery(namedQuery, Account.class);
			query.setParameter("tokenHash", tokenHash);
			query.setParameter("type", type);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public Account findAccount(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find account by username {}", email);
		try {
			final String namedQuery = "Account.findAccount";
			final TypedQuery<Account> query = em.createNamedQuery(namedQuery, Account.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public void changePassword(final String email, final String oldPassword, final String newPassword) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(oldPassword);
		Objects.requireNonNull(newPassword);
		final Account account = this.findAccount(email);
		if (account.isValid(oldPassword)) {
			account.setPassword(newPassword);
		}
	}
}
