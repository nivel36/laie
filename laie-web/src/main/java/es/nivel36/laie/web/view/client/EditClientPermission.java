package es.nivel36.laie.web.view.client;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.SessionUser;
import jakarta.inject.Inject;

public class EditClientPermission implements ClientPermission {

	private @Inject SessionUser sessionUser;

	public boolean validate(final Client client) {
		final User user = sessionUser.get();
		if (sessionUser.isAdmin()) {
			return true;
		}
		final User owner = client.getOwner();
		if (owner.equals(user)) {
			return true;
		}
		if (sessionUser.isManagerOf(owner)) {
			return true;
		}
		return false;
	}
}
