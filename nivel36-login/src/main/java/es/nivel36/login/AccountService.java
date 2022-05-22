package es.nivel36.login;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import es.nivel36.login.LoginToken.TokenType;

@Stateless
public class AccountService {

	private @PersistenceContext(unitName = "nivel36-login") EntityManager em;

	public Account findUserByTokenHashAndType(final byte[] tokenHash, final TokenType type) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(type);
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
		try {
			final String namedQuery = "Account.findAccount";
			final TypedQuery<Account> query = em.createNamedQuery(namedQuery, Account.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public void changePassword(final String email, final String newPassword) {

	}
}
