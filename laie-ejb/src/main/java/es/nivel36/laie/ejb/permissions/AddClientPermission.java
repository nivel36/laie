package es.nivel36.laie.ejb.permissions;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.user.User;

public class AddClientPermission extends AbstractClientPermission {

	@Override
	public boolean validate(Client entity, User user) {
		return true;
	}
}
