package ged.web.view.client;

import javax.inject.Inject;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractBean;

public abstract class AbstractClientBean extends AbstractBean {

	private static final long serialVersionUID = -6560496691505294872L;
	
	protected Client client;

	@Inject
	protected transient ClientService clientService;

	public Client getClient() {
		return this.client;
	}

	protected String gotoClientPage() {
		return PageEnum.CLIENT.getRedirectUrl(this.client);
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

}
