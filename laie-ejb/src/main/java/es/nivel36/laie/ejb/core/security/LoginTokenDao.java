package es.nivel36.laie.ejb.core.security;

import static es.nivel36.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.core.model.AbstractDao;
import es.nivel36.core.model.Page;
import es.nivel36.core.model.Repository;
import es.nivel36.core.util.Parameters;

@Repository
public class LoginTokenDao extends AbstractDao {

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

	public LoginToken findByTokenHash(final byte[] tokenHash) {
		Objects.requireNonNull(tokenHash);
		final String namedQuery = "LoginToken.findByTokenHash";
		final Parameters parameters = map("tokenHash", tokenHash);
		return this.findByQuery(LoginToken.class, namedQuery, parameters);
	}

	public List<LoginToken> findExpiredTokens() {
		final String namedQuery = "LoginToken.findExpired";
		return this.findByQuery(LoginToken.class, namedQuery, null, Page.ALL_RESULTS);
	}
}