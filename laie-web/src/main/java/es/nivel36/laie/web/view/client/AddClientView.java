package es.nivel36.laie.web.view.client;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.client.DuplicateCifException;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddClientView extends AbstractView {

	private static final long serialVersionUID = -5675968370983284897L;

	private static final Logger logger = LoggerFactory.getLogger(AddClientView.class);

	protected Client client;

	@Inject
	protected transient ClientService clientService;

	@PostConstruct
	public void init() {
		logger.trace("New client init");
		this.client = new Client();
		this.client.setOwner(this.sessionUser.get());
	}

	public void save() {
		logger.debug("Add new client action performed");
		try {
			this.clientService.addClient(this.client);
			final String url = ViewClientView.URL + "?client=" + this.client.getId();
			Faces.redirect(url);
		} catch (final DuplicateCifException e) {
			this.addErrorToField("clientForm:cif", "client.error.cif_exists");
		}
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setClientService(final ClientService clientService) {
		Objects.requireNonNull(clientService);
		this.clientService = clientService;
	}
}