package es.nivel36.laie.web.view.client;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractClientView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	protected Client client;

	@Inject
	protected transient ClientService clientService;

	protected String clientUrl() {
		return this.navigator.getRedirectUrl(PageEnum.CLIENT, this.client);
	}

	public Client getClient() {
		return this.client;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

}
