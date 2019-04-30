package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.web.core.util.Navigate;

@Named
@ViewScoped
public class SelectClientBean extends AbstractClientSearch {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -9105788652207124873L;

	private Client selectedClient;

	private String returnUrl;

	public void init() {
		this.returnUrl = getValueFromFlash("url");
		refreshState();
	}

	public void refreshState() {
		this.putValueToFlash("state", this.getValueFromFlash("state"));
	}

	public String cancel() {
		return returnUrl;
	}

	public Client getSelectedClient() {
		return this.selectedClient;
	}

	public void onClientSelect() {
		logger.debug("Select client action performed");
		this.putValueToFlash("client", selectedClient);
		Navigate.to(returnUrl);
	}

	public void setSelectedClient(final Client selectedClient) {
		this.selectedClient = selectedClient;
	}
}