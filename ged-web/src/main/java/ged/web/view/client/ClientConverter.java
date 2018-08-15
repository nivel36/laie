package ged.web.view.client;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.AbstractService;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = Client.class)
public class ClientConverter extends AbstractConverter<Client> {

	@Inject
	private ClientService clientService;

	@Override
	protected AbstractService<Client> getService() {
		return this.clientService;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}
}
