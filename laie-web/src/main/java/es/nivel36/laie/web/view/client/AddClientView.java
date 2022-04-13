package es.nivel36.laie.web.view.client;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;

@Named
@ViewScoped
public class AddClientView extends AbstractClientView {

	private static final long serialVersionUID = -5675968370983284897L;

	private static final Logger logger = LoggerFactory.getLogger(AddClientView.class);

	@PostConstruct
	public void init() {
		logger.trace("New client init");
		this.client = new Client();
		this.client.setOwner(this.sessionUser.get());
	}

	public void save() {
		logger.debug("Add new client action performed");
		this.clientService.addClient(this.client);
		final String url = ViewClientView.URL + "?client=" + this.client.getId();
		Faces.redirect(url);
	}
}