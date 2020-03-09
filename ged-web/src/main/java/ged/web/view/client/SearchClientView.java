package ged.web.view.client;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.ClientService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchClientView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private ClientLazyDataModel clients;

	@Inject
	private transient ClientService clientService;

	private String searchText;

	public void export() {
		logger.debug("Export clients action performed");
	}

	public ClientLazyDataModel getClients() {
		return this.clients;
	}

	public String getSearchText() {
		return this.searchText;
	}

	@PostConstruct
	public void init() {
		logger.debug("Client search init");
		this.clients = new ClientLazyDataModel(this.clientService);
	}

	public void search() {
		logger.debug("Search clients action performed");
		this.clients.setSearchText(this.searchText);
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}