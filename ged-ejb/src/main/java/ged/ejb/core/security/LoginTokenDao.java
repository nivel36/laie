package ged.ejb.core.security;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;

@Repository
public class LoginTokenDao extends AbstractDao<LoginToken> {

	public LoginToken findByTokenHash(final String tokenHash) {
		Objects.requireNonNull(tokenHash);
		return this.getPersistenceFacade().findByQuery(LoginToken.class, "LoginToken.findByTokenHash",
				map("tokenHash", tokenHash));
	}

	public List<LoginToken> findExpiredTokens() {
		return this.getPersistenceFacade().findByQuery(LoginToken.class, "LoginToken.findExpired", null, Page.ALL_RESULTS);
	}

	@Override
	protected Class<LoginToken> getType() {
		return LoginToken.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}
