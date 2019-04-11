package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.Address;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class ClientEditBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	private Client buildNewClient() {
		final Client newClient = new Client();
		newClient.setOwner(this.sessionUser.get());
		newClient.setAddress(new Address());
		return newClient;
	}

	private void editClientInit() {
		logger.debug("Client {} edit init", client.getName());
	}

	public Client getClient() {
		return this.client;
	}

	@PostConstruct
	public void init() {
		this.client = this.getValueFromFlash("client");
		if (this.client == null) {
			newClientInit();
		}
		else {
			editClientInit();
		}
	}

	public boolean isNewClient() {
		if (this.client == null) {
			throw new IllegalStateException("Null client");
		}
		return this.client.getId() == 0;
	}

	private void newClientInit() {
		logger.debug("New client init");
		this.client = this.buildNewClient();
	}

	public String save() {
		logger.debug("Save client action performed");
		this.client = this.clientService.save(this.client);
		return "/client/client?faces-redirect=true&clientId=" + this.client.getId();
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}
}