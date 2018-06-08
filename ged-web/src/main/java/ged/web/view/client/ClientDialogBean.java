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
import ged.web.core.GedPermissionException;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class ClientDialogBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	private Client buildNewClient() {
		final Client newClient = new Client();
		newClient.setOwner(this.sessionBean.getUser());
		newClient.setAddress(new Address());
		return newClient;
	}

	public Client getClient() {
		return this.client;
	}

	@PostConstruct
	public void init() {
		logger.trace("ClientDialog oppened");
		final Long clientId = this.getIdFromParameters("clientId");
		if (clientId != null) {
			this.client = this.clientService.find(clientId);
			if (this.client == null) {
				throw new IllegalStateException();
			}
			if (this.client.getAddress() == null) {
				this.client.setAddress(new Address());
			}
		}
		else {
			this.client = this.buildNewClient();
		}
	}

	private void insertClient() {
		this.clientService.insert(this.client);
		this.closeDialog(this.client);
	}

	public boolean isNewClient() {
		if (this.client == null) {
			return true;
		}
		return this.client.getId() == 0;
	}

	public void save() {
		logger.debug("ClientDialog save action performed");
		if (this.isNewClient()) {
			this.insertClient();
		}
		else {
			this.updateClient();
		}
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	private void updateClient() {
		if (!this.userHasPermissionToEdit(this.client)) {
			throw new GedPermissionException();
		}
		this.client = this.clientService.update(this.client);
		this.closeDialog(this.client);
	}
}