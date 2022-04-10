package es.nivel36.laie.web.view.client;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditClientView extends AbstractClientView {

	private static final long serialVersionUID = 1356969048613753901L;

	private static final Logger logger = LoggerFactory.getLogger(EditClientView.class);

	public static final String URL = "/client/edit.xhtml";

	@PostConstruct
	public void init() {
		if (this.client == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Client {} edit init", this.client);
	}

	public void save() {
		logger.debug("Save client action performed");
		this.client = this.clientService.updateClient(this.client);
		Faces.redirect(this.clientUrl());
	}
}