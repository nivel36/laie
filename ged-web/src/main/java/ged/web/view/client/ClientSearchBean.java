package ged.web.view.client;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.web.core.view.AbstractPageBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class ClientSearchBean extends AbstractPageBean {

	private static final transient Logger logger = Logger.getLogger(ClientSearchBean.class.getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private final transient ClientService clientService;

	private String name;

	private Paginator<Client> paginator;

	@Inject
	public ClientSearchBean(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}

	public void clean() {
		logger.fine("Clean action performed");
		this.name = null;
		search();
	}

	public String edit(final Client client) {
		logger.fine("Edit candidate action performed");
		this.flash.put("client", client);
		return "clientEdit?faces-redirect=true";
	}

	public String getName() {
		return this.name;
	}

	public Paginator<Client> getPaginator() {
		return this.paginator;
	}

	@PostConstruct
	public void init() {
		logger.finest("Init ClientSearchBean");
		this.paginator = new Paginator<>(this.sessionBean.getRowsPerPage());
		this.paginator.setEntities(this.clientService.searchByName(this.name));
	}

	public String newClient() {
		logger.fine("New client action performed");
		return "clientEdit?faces-redirect=true";
	}

	public void remove(final Client client) {
		logger.fine("Removing client action performed");
		this.clientService.delete(client);
		search();
	}

	public void search() {
		logger.fine("Searching for client action performed");
		final List<Client> clients = this.clientService.searchByName(this.name);
		this.paginator.setEntities(clients);
	}

	public void setName(final String name) {
		this.name = name;
	}
}