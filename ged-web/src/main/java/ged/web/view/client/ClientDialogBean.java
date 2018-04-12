package ged.web.view.client;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.CLIENT;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.Address;
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

	@Override
	protected void dispose() {
		logger.trace("ClientDialog closed");
		this.client = null;

	}

	public Client getClient() {
		return this.client;
	}

	@Override
	protected void init() {
		logger.trace("ClientDialog oppened");
		this.client = this.getAttribute("client");
		if (this.client == null) {
			this.client = this.buildNewClient();
		}
	}

	private void insertClient() {
		this.clientService.insert(this.client);
		to(CLIENT).withParams(map("clientId", this.client.getId())).doGet();
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
		this.client = this.clientService.update(this.client);
		this.callback.onCloseDialog(this.client);
		Ajax.update("clientForm", this.updateField);
		this.closeDialog();
	}
}