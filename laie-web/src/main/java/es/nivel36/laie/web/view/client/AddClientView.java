package es.nivel36.laie.web.view.client;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.DuplicateCifException;
import es.nivel36.laie.ejb.core.model.Address;
import es.nivel36.laie.ejb.user.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class AddClientView extends AbstractClientView {

	private static final long serialVersionUID = -5675968370983284897L;

	private static final Logger logger = LoggerFactory.getLogger(AddClientView.class);
	
	private transient @Inject AddClientPermission addClientPermission;

	@PostConstruct
	public void init() {
		logger.trace("New client init");
		final User user = this.sessionUser.get();
		this.checkPermissions();
		this.client = new Client();
		this.client.setAddress(new Address());
		this.client.setOwner(user);
	}

	private void checkPermissions() {
		if (!this.addClientPermission.validate(client)) {
			throw new SecurityException();
		}
	}

	public void save() {
		logger.debug("Add new client action performed");
		try {
			this.clientService.addClient(this.client);
			final String url = ViewClientView.getUrl(this.client.getId());
			Faces.redirect(url);
		} catch (final DuplicateCifException e) {
			this.addErrorToField("clientForm:cif", "client.error.cif_exists");
		}
	}
}