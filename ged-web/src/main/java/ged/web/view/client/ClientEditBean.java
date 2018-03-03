package ged.web.view.client;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Page.CLIENT;

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

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	public Client buildNewClient() {
		final Client newClient = new Client();
		newClient.setOwner(this.sessionBean.getUser());
		newClient.setAddress(new Address());
		return newClient;
	}

	public void clear() {
		this.client = null;
	}

	public Client getClient() {
		return this.client;
	}

	public void init() {
		this.client = buildNewClient();
	}

	public void save() {
		this.clientService.insert(this.client);
		Navigate.to(CLIENT).withParams(map("id", this.client.getId())).doGet();
	}
}