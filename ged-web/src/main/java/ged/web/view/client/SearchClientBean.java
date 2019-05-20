package ged.web.view.client;

import java.io.IOException;
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
import ged.ejb.core.model.Page;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class SearchClientBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Client> clients;

	@Inject
	private transient ClientService clientService;

	private String searchText;

	public void export() throws IOException {
		logger.debug("Export clients action performed");
	}

	public List<Client> getClients() {
		return this.clients;
	}

	public String getSearchText() {
		return this.searchText;
	}

	@PostConstruct
	public void init() {
		logger.debug("Client search init");
		this.search();
	}

	public void search() {
		logger.debug("Search clients action performed");
		this.clients = this.clientService.search(this.searchText, Page.ALL).getResultData();
		this.addWarningMessageIfMaxSearchResultsHaveBeenReached(this.clients);
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}