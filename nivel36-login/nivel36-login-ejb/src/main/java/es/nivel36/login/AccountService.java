package es.nivel36.login;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.login.LoginToken.TokenType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	private @PersistenceContext(unitName = "nivel36-login") EntityManager em;

	public Account findAccountByTokenHashAndType(final String tokenHash, final TokenType type) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(type);
		logger.debug("Find account by tokenHash {} and type {}", tokenHash, type);
		final String namedQuery = "Account.findByTokenHashAndType";
		final TypedQuery<Account> query = em.createNamedQuery(namedQuery, Account.class);
		query.setParameter("tokenHash", tokenHash);
		query.setParameter("type", type);
		return query.getSingleResult();
	}

	public Account findAccount(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find account by username {}", email);
		final String namedQuery = "Account.findAccount";
		final TypedQuery<Account> query = em.createNamedQuery(namedQuery, Account.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}

	public void changePassword(final String email, final String oldPassword, final String newPassword) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(oldPassword);
		Objects.requireNonNull(newPassword);
		final Account account = this.findAccount(email);
		if (account.isValid(oldPassword)) {
			account.setPassword(newPassword);
			this.em.merge(account);
		}
	}
}
