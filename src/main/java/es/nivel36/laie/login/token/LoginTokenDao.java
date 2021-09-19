package es.nivel36.laie.login.token;

import java.util.List;
import java.util.Objects;

import javax.persistence.TypedQuery;

import es.nivel36.laie.core.service.AbstractDao;
import es.nivel36.laie.core.service.Repository;

@Repository
public class LoginTokenDao extends AbstractDao {

	public LoginToken findByTokenHash(final byte[] tokenHash) {
		Objects.requireNonNull(tokenHash);
		final TypedQuery<LoginToken> query = this.entityManager.createNamedQuery("LoginToken.findByTokenHash",
				LoginToken.class);
		query.setParameter("tokenHash", tokenHash);
		return query.getSingleResult();
	}

	public List<LoginToken> findExpiredTokens(final int page, final int pageSize) {
		this.validatePagination(page, pageSize);
		final TypedQuery<LoginToken> query = this.entityManager.createNamedQuery("LoginToken.findExpired",
				LoginToken.class);
		this.paginate(page, pageSize, query);
		return query.getResultList();
	}
	
	public void delete(LoginToken loginToken) {
		Objects.requireNonNull(loginToken);
		this.entityManager.remove(loginToken);
	}
	
	public void insert(LoginToken loginToken) {
		Objects.requireNonNull(loginToken);
		this.entityManager.persist(loginToken);
	}
}