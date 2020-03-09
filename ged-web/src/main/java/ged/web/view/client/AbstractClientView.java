package ged.web.view.client;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

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
