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
public class GedSecurityContext {

	@Resource
	protected SessionContext sessionContext;

	@Inject
	private UserService userService;

	public boolean canEdit(final Ownerable entity) {
		Objects.requireNonNull(entity);
		final String username = this.extractUsernameFromPrincipal();
		final User entityOwner = entity.getOwner();
		if (username.equals(entityOwner.getEmail())) {
			return true;
		}
		final User sessionUser = this.getUserByUsername(username);
		if (sessionUser.isAdmin()) {
			return true;
		}
		return this.userService.isSubordinateUser(sessionUser.getEmail(), entityOwner.getEmail());
	}

	private String extractUsernameFromPrincipal() {
		final Principal principal = this.sessionContext.getCallerPrincipal();
		return principal.getName();
	}

	public User getLoggedUser() {
		final String username = this.sessionContext.getCallerPrincipal().getName();
		return this.getUserByUsername(username);
	}

	private User getUserByUsername(final String username) {
		return this.userService.findByEmail(username);
	}

	public String getUsername() {
		return this.sessionContext.getCallerPrincipal().getName();
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
}
