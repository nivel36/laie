package es.nivel36.laie.web.view.client;

import java.util.Objects;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

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
