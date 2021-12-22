package es.nivel36.laie.web.view.client;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditClientView extends AbstractClientView {

	private static final long serialVersionUID = 1356969048613753901L;

	private static final Logger logger = LoggerFactory.getLogger(EditClientView.class);

	private String uid;

	@PostConstruct
	public void init() {
		this.uid = this.getValueFromGetParameters("client");
		if (this.uid == null) {
			throw new IllegalPageStateException();
		}
		this.client = clientService.findClientByUid(uid);
		if (this.client == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Client {} edit init", this.client);
	}
	
	public void addBookmark() {
		logger.debug("Add bokmark action performed");
	}

	public String save() {
		logger.debug("Save client action performed");
		this.clientService.updateClient(client);
		return this.clientUrl();
	}
}