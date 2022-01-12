package es.nivel36.laie.web.view.client;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.ClientDto;

@Named
@ViewScoped
public class AddClientView extends AbstractClientView {

	private static final long serialVersionUID = -5675968370983284897L;
	
	private static final Logger logger = LoggerFactory.getLogger(AddClientView.class);

	@PostConstruct
	public void init() {
		logger.trace("New client init");
		this.client = new ClientDto();
	}

	public void save() {
		logger.debug("Add new client action performed");
		final String ownerUid = this.sessionUser.get().getUid();
		this.client = this.clientService.addClient(this.client, ownerUid);
		final String clientUrl = this.clientUrl();
		Faces.redirect(clientUrl);
	}
}