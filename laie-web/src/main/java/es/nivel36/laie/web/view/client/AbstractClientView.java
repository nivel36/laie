package es.nivel36.laie.web.view.client;

import java.util.Objects;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractClientView extends AbstractView {

	private static final long serialVersionUID = 3770146257248125858L;

	@Param
	protected Client client;

	@Inject
	protected transient ClientService clientService;

	public Client getClient() {
		return this.client;
	}

	public void setClientService(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}
}
