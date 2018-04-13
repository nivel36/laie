package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.Address;
import ged.web.core.CloseDialogListener;
import ged.web.core.PageNotFoundException;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientBean extends AbstractBean implements CloseDialogListener {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	private Long clientId;

	@Inject
	private transient ClientService clientService;

	public void export() {
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
		logger.trace("Init ClientBean");
		if (this.clientId == null) {
			logger.error("ClientId is null");
			throw new PageNotFoundException();
		}
		this.client = this.clientService.find(this.clientId);
		if (this.client == null) {
			logger.error("Client mot found");
			throw new PageNotFoundException();
		}
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		if (this.client.isDeleted()) {
			logger.warn("Client was erased");
			Message.addWarning("message.erased_entity", "message.erased_entity");
		}
	}

	public boolean isUserHasPermissionToEdit() {
		return this.userHasPermissionToEdit(this.client);
	}

	@Override
	public void onCloseDialog(final Object value) {
		this.client = (Client) value;
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