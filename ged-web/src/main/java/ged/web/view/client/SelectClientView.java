package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SelectClientView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private ClientLazyDataModel clients;

	@Inject
	private ClientService clientService;

	private String searchText;

	private Client selectedClient;

	public void cancel() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public ClientLazyDataModel getClients() {
		return this.clients;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public Client getSelectedClient() {
		return this.selectedClient;
	}

	@PostConstruct
	public void init() {
		logger.debug("Client select init");
		this.clients = new ClientLazyDataModel(this.clientService);
	}

	public void onClientSelect() {
		logger.debug("Select client action performed");
		PrimeFaces.current().dialog().closeDynamic(this.selectedClient);
	}

	public void search() {
		this.clients.setSearchText(this.searchText);
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedClient(final Client selectedClient) {
		this.selectedClient = selectedClient;
	}
}