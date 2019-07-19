package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.core.model.Address;

@Named
@ViewScoped
public class AddClientView extends AbstractClientView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	private Client buildNewClient() {
		final Client newClient = new Client();
		newClient.setOwner(this.sessionUser.get());
		newClient.setAddress(new Address());
		return newClient;
	}

	@PostConstruct
	public void init() {
		logger.trace("New client init");
		this.client = this.buildNewClient();
	}

	public String save() {
		logger.debug("Create new client action performed");
		this.client = this.clientService.save(this.client);
		return this.clientUrl();
	}
}