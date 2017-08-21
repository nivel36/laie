package ged.web.view.client;

import java.util.List;
import java.util.Objects;

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

	private static final transient Logger logger = LoggerFactory.getLogger(ClientSearchBean.class.getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private final transient ClientService clientService;

	private String name;

	private List<Client> clients;

	@Inject
	public ClientSearchBean(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}

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