package es.nivel36.laie.web.view.client;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.client.DuplicateCifException;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditClientView extends AbstractClientView {

	private static final long serialVersionUID = 1356969048613753901L;

	private static final Logger logger = LoggerFactory.getLogger(EditClientView.class);

	@PostConstruct
	public void init() {
		if (this.client == null) {
			throw new IllegalPageStateException();
		}
		final User user = sessionUser.get();
		if (!this.client.getOwner().equals(user)) {
			throw new SecurityException();
		}
		logger.trace("Client {} edit init", this.client);
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