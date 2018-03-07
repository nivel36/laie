package ged.web.view.client;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.web.core.ActionCallback;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class ClientSearchDialogBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -9105788652207124873L;

	private List<Client> clients;

	@Inject
	private transient ClientService clientService;

	private String searchText;

	private ActionCallback<Client> selectClientActionCallback;

	private Client selectedClient;

	private String updateField;

	public void clean() {
		logger.debug("Clean action performed");
		this.searchText = null;
		search();
	}

	@Override
	protected void dispose() {
		this.searchText = null;
	}

	public List<Client> getClients() {
		return this.clients;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public Client getSelectedClient() {
		return this.selectedClient;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		logger.trace("Init ClientSearchDialogBean");
		this.selectClientActionCallback = getAttribute(ActionCallback.class, "callback");
		this.updateField = getAttribute(String.class, "updateField");
		this.clients = this.clientService.search(this.searchText);
	}

	public void onClientSelect() {
		this.selectClientActionCallback.doAction(this.selectedClient);
		closeDialog();
		Ajax.update(this.updateField);
	}

	public void search() {
		logger.debug("Searching for client action performed");
		this.clients = this.clientService.search(this.searchText);
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectClientActionCallback(final ActionCallback<Client> selectClientActionCallback) {
		this.selectClientActionCallback = selectClientActionCallback;
	}

	public void setSelectedClient(final Client selectedClient) {
		this.selectedClient = selectedClient;
	}
}