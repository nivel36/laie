package es.nivel36.laie.web.view.client;

import es.nivel36.laie.ejb.client.Client;

public class AddClientPermission implements ClientPermission {

	@Override
	public boolean validate(Client entity) {
		return true;
	}
}
