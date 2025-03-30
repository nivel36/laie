package es.nivel36.laie.web.view.client;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.DuplicateCifException;
import es.nivel36.laie.web.core.IllegalPageStateException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class EditClientView extends AbstractClientView {

	private static final long serialVersionUID = 1356969048613753901L;

	private static final Logger logger = LoggerFactory.getLogger(EditClientView.class);


	private transient @Inject EditClientPermission permission;

	@PostConstruct
	public void init() {
		if (this.client == null) {
			throw new IllegalPageStateException();
		}
		this.checkEditPermissions();
		logger.trace("Client {} edit init", this.client);
	}

	private void checkEditPermissions() {
		if (!permission.validate(client)) {
			throw new SecurityException();
		}
	}

	public void save() {
		logger.debug("Save client action performed");
		try {
			this.client = this.clientService.updateClient(this.client);
			final String url = ViewClientView.getUrl(this.client.getId());
			Faces.redirect(url);
		} catch (final DuplicateCifException e) {
			this.addErrorToField("clientForm:cif", "client.error.cif_exists");
		}
	}
}