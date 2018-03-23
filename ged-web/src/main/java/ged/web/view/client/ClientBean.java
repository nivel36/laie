package ged.web.view.client;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.Address;
import ged.web.core.PageNotFoundException;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientBean extends AbstractBean {

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	private Long clientId;

	@Inject
	private transient ClientService clientService;

	public void export() {
		// TODO
	}

	public Client getClient() {
		return this.client;
	}

	public Long getClientId() {
		return this.clientId;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		this.client = this.clientService.find(this.clientId);
		if (this.client == null) {
			throw new PageNotFoundException();
		}
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		if (this.client.isDeleted()) {
			Message.addWarning("message.erased_entity", "message.erased_entity");
		}
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setClientId(final Long clientId) {
		this.clientId = clientId;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}
}