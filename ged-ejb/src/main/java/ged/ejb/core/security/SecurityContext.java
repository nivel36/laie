package ged.ejb.core.security;

import java.security.Principal;
import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.model.Ownerable;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Stateless
public class SecurityContext {

	@Resource
	protected SessionContext sessionContext;

	@Inject
	private UserService userService;

	public boolean canEdit(final Ownerable entity) {
		Objects.requireNonNull(entity);
		final String username = extractUsernameFromPrincipal();
		final User entityOwner = entity.getOwner();
		if (username.equals(entityOwner.getEmail())) {
			return true;
		}
		final User sessionUser = getUserByUsername(username);
		if (sessionUser.isAdmin()) {
			return true;
		}
		return userService.isSubordinateUser(sessionUser, entityOwner);
	}

	private String extractUsernameFromPrincipal() {
		final Principal principal = sessionContext.getCallerPrincipal();
		return principal.getName();
	}

	private User getUserByUsername(final String username) {
		return userService.findUserByEmail(username);
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
}
