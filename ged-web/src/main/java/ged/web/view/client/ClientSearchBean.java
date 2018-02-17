package ged.web.view.client;

import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.CLIENT;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import static ged.ejb.core.model.FluentHashMap.*;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientSearchBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Client> clients;

	@Inject
	private transient ClientService clientService;

	private String searchText;

	private Client selectedClient;

	public void clean() {
		logger.debug("Clean action performed");
		this.searchText = null;
		search();
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

	@PostConstruct
	public void init() {
		logger.trace("Init ClientSearchBean");
		this.clients = this.clientService.search(this.searchText);
	}

	public String newClient() {
		logger.debug("New client action performed");
		return to(CLIENT).toUrl();
	}

	public void onClientSelect() {
		to(CLIENT).withParams(map("id", this.selectedClient.getId())).doGet();
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

	public void setSelectedClient(final Client selectedClient) {
		this.selectedClient = selectedClient;
	}
}