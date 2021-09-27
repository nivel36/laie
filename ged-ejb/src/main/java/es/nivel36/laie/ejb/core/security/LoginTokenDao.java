package es.nivel36.laie.ejb.core.security;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;

@Repository
public class LoginTokenDao extends AbstractDao<LoginToken> {

	public LoginToken findByTokenHash(final byte[] tokenHash) {
		Objects.requireNonNull(tokenHash);
		return this.getPersistenceFacade().findByQuery(LoginToken.class, "LoginToken.findByTokenHash",
				map("tokenHash", tokenHash));
	}

	public List<LoginToken> findExpiredTokens() {
		return this.getPersistenceFacade().findByQuery(LoginToken.class, "LoginToken.findExpired", null,
				Page.ALL_RESULTS);
	}

	@Override
	protected Class<LoginToken> getType() {
		return LoginToken.class;
	}
}