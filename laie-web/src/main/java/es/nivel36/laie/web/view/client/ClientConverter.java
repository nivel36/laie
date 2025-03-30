package es.nivel36.laie.web.view.client;

import java.util.Objects;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = Client.class)
public class ClientConverter extends AbstractConverter<Client> {

	@Inject
	private ClientService clientService;

	@Override
	protected Client getAsObject(Long id) {
		return clientService.findClientById(id);
	}

	public void setClientService(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}
}