package ged.web.view.client;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.model.Page;
import ged.web.core.view.AbstractBean;

public class AbstractClientSearch extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -7600701013648781865L;

	private List<Client> clients;

	@Inject
	private transient ClientService clientService;

	private String searchText;

	public List<Client> getClients() {
		return clients;
	}

	public String getSearchText() {
		return searchText;
	}

	public void search() {
		logger.debug("Search clients action performed");
		this.clients = this.clientService.search(this.searchText, Page.ALL);
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}
