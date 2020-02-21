package ged.web.view.client;

import java.util.Objects;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.AbstractIndexedService;
import ged.web.core.view.AbstractLazyDataModel;

public class ClientLazyDataModel extends AbstractLazyDataModel<Client> {

	private static final long serialVersionUID = 1L;

	private transient ClientService clientService;

	public ClientLazyDataModel(final ClientService clientService) {
		Objects.requireNonNull(clientService, "ClientService can't be null");
		this.clientService = clientService;
	}

	@Override
	protected AbstractIndexedService<Client> getService() {
		return this.clientService;
	}
}
