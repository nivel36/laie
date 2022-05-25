package es.nivel36.laie.ejb.permissions;

import javax.inject.Inject;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

public class EditClientPermission extends AbstractClientPermission {

	private @Inject UserService userService;

	public boolean validate(final Client client, final User user) {
		if (user.isAdmin()) {
			return true;
		}
		final User owner = client.getOwner();
		if (owner.equals(user)) {
			return true;
		}
		if (userService.isSubordinateUser(owner, user)) {
			return true;
		}
		return false;
	}
}
