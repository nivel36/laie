package ged.web.view.client;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.Address;
import ged.web.core.util.Navigate;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientEditBean extends AbstractBean {

	private static final long serialVersionUID = 2262878574773282127L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	public String cancel() {
		return Navigate.clientSearchUrl();
	}

	public Client getClient() {
		return this.client;
	}

	@PostConstruct
	private void init() {
		if (this.flash.containsKey("client")) {
			this.client = (Client) this.flash.get("client");
		} else {
			this.client = new Client();
		}
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		this.flash.put("candidate", this.client);
	}

	public String save() {
		saveClient();
		return "clientView.xhtml?id=" + this.client.getId() + "&faces-redirect=true";
	}

	private void saveClient() {
		this.client = this.clientService.update(this.client);
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}
}