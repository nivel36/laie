package es.nivel36.laie.ejb.permissions;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class SecurityService {

	private @Inject EditClientPermission editClientPermission;

	private @Inject AddClientPermission addClientPermission;

	public boolean canEditClient(final Client client, final User user) {
		return editClientPermission.validate(client, user);
	}

	public boolean canAddClient(final Client client, final User user) {
		return addClientPermission.validate(client, user);
	}
}