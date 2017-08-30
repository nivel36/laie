package ged.web.view.client;

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
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class ClientSearchBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	@Inject
	private transient ClientService clientService;

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	private String name;

	private List<Client> clients;

	public void clean() {
		logger.debug("Clean action performed");
		this.name = null;
		search();
	}

	public String edit(final Client client) {
		logger.debug("Edit candidate action performed");
		this.flash.put("client", client);
		return "clientEdit?faces-redirect=true";
	}

	public String getName() {
		return this.name;
	}

	public List<Client> getClients() {
		return this.clients;
	}

	@PostConstruct
	public void init() {
		logger.trace("Init ClientSearchBean");
		this.clients = this.clientService.searchByName(this.name);
	}

	public String newClient() {
		logger.debug("New client action performed");
		return "clientEdit?faces-redirect=true";
	}

	public void remove(final Client client) {
		logger.debug("Removing client action performed");
		this.clientService.delete(client);
		search();
	}

	public void search() {
		logger.debug("Searching for client action performed");
		clients = this.clientService.searchByName(this.name);
	}

	public void setName(final String name) {
		this.name = name;
	}
}