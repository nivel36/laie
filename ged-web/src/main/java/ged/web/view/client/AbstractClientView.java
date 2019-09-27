package ged.web.view.client;

import javax.inject.Inject;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

public abstract class AbstractClientView extends AbstractView {

	private static final long serialVersionUID = 1L;

	protected Client client;

	@Inject
	protected transient ClientService clientService;

	protected String clientUrl() {
		return PageEnum.CLIENT.getRedirectedUrl(this.client);
	}

	public Client getClient() {
		return this.client;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

}
