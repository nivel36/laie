package ged.web.view.client;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.util.Parameters;
import ged.web.core.util.Navigate;
import ged.web.core.util.PageEnum;
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

	public void clean() {
		logger.debug("Clean action performed");
		this.searchText = null;
		this.search();
	}

	public List<Client> getClients() {
		return this.clients;
	}

	public String getSearchText() {
		return this.searchText;
	}

	@PostConstruct
	public void init() {
		logger.trace("Init ClientSearchBean");
		this.clients = this.clientService.search(this.searchText);
	}

	public void onCloseClientDialog(final SelectEvent e) {
		final Client client = (Client) e.getObject();
		if (client != null) {
			Navigate.to(PageEnum.CLIENT).withParams(Parameters.map("clientId", client.getId())).doGet();
		}
	}

	public void openNewClientDialog() {
		this.openDialog("clientDialog", null);
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
}