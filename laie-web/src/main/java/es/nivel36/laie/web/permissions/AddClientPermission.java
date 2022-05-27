package es.nivel36.laie.web.permissions;

import es.nivel36.laie.ejb.client.Client;

public class AddClientPermission extends AbstractClientPermission {

	@Override
	public boolean validate(Client entity) {
		return true;
	}
}
