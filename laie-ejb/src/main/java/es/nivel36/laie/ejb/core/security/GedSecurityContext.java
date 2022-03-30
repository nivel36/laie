package es.nivel36.laie.ejb.core.security;

import java.security.Principal;
import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.core.model.Ownerable;
import es.nivel36.core.model.Repository;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class GedSecurityContext {

	@Resource
	protected SessionContext sessionContext;
	
	@Inject
	@Repository
	private UserDao userDao;

	public boolean canEdit(final Ownerable entity) {
		Objects.requireNonNull(entity);
		final String username = this.extractUsernameFromPrincipal();
		final User entityOwner = (User)entity.getOwner();
		if (username.equals(entityOwner.getEmail())) {
			return true;
		}
		final User sessionUser = this.userDao.findUserByEmail(username);
		if (sessionUser.isAdmin()) {
			return true;
		}
		return this.userDao.isSubordinateUser(sessionUser, entityOwner);
	}

	private String extractUsernameFromPrincipal() {
		final Principal principal = this.sessionContext.getCallerPrincipal();
		return principal.getName();
	}

	public User getLoggedUser() {
		final String username = this.sessionContext.getCallerPrincipal().getName();
		return this.userDao.findUserByEmail(username);
	}

	public String getUsername() {
		return this.sessionContext.getCallerPrincipal().getName();
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
}
