package ged.web.view.client;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;

@Named
@ViewScoped
public class SelectClientBean extends AbstractClientSearch {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = -9105788652207124873L;

	private Client selectedClient;

	@PostConstruct
	public void init() {
		search();
	}

	public Client getSelectedClient() {
		return this.selectedClient;
	}

	public void onClientSelect() {
		logger.debug("Select client action performed");
		PrimeFaces.current().dialog().closeDynamic(selectedClient);
	}

	public void setSelectedClient(final Client selectedClient) {
		this.selectedClient = selectedClient;
	}
}