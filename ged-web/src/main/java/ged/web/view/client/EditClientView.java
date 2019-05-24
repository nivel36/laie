package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.web.core.IllegalPageStateException;

@Named
@ViewScoped
public class EditClientView extends AbstractClientView {

	private static final String CLIENT_KEY = "client";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	private void checkEditPermission() {
		if (!this.sessionUser.hasPermissionToEdit(this.client)) {
			logger.error("User {} hasn't got priviliges to edit client {}", this.sessionUser.get(), this.client);
			throw new SecurityException();
		}
	}

	private void checkNonNullClient() {
		if (this.client == null) {
			logger.error("Trying to edit a client but client is null");
			throw new IllegalPageStateException();
		}
	}

	@PostConstruct
	public void init() {
		this.client = this.getValueFromFlash(CLIENT_KEY);
		this.checkNonNullClient();
		logger.trace("Client {} edit init", this.client);
		this.checkEditPermission();
		this.putValueToFlash(CLIENT_KEY, this.client); // prevent errors if f5/reload is pressed
	}

	public String save() {
		logger.debug("Save client action performed");
		this.client = this.clientService.save(this.client);
		return this.clientUrl();
	}
}