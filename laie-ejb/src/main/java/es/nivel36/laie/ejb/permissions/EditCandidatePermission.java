package es.nivel36.laie.ejb.permissions;

import javax.inject.Inject;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

public class EditCandidatePermission extends AbstractCandidatePermission {

	private @Inject UserService userService;

	@Override
	public boolean validate(final User user) {
		if (user.isAdmin()) {
			return true;
		}
		final User owner = candidate.getOwner();
		if (owner.equals(user)) {
			return true;
		}
		if (userService.isSubordinateUser(owner, user)) {
			return true;
		}
		return false;
	}

}
