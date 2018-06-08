package ged.web.view.client;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.JOB_OFFER;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.client.Contact;
import ged.ejb.core.Address;
import ged.ejb.job.offer.JobOffer;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@Param
	private Long clientId;

	@Inject
	private transient ClientService clientService;

	private List<Contact> contacts;

	public void export() {
	}

	public Client getClient() {
		return this.client;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public List<JobOffer> getJobOffers() {
		return new ArrayList<>(this.client.getJobOffers());
	}

	@PostConstruct
	public void init() {
		logger.trace("Init ClientBean");
		this.client = this.clientService.findAllClientDataByClientId(this.clientId);

		if (this.client == null) {
			logger.error("Client not found");
			throw new PageNotFoundException();
		}

		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}

		this.contacts = new ArrayList<>(this.client.getContacts());
	}

	public boolean isUserHasPermissionToEdit() {
		return this.userHasPermissionToEdit(this.client);
	}

	public void onCloseClientDialog(final SelectEvent event) {
		final Client clientFromDialog = (Client) event.getObject();
		if (clientFromDialog != null) {
			this.client = clientFromDialog;
		}
	}

	public void onCloseContactDialog(final SelectEvent e) {
		final Contact newContact = (Contact) e.getObject();
		if (newContact != null) {
			this.contacts.add(newContact);
		}
	}

	public void onCloseJobOfferDialog(final SelectEvent e) {
		final JobOffer newJobOffer = (JobOffer) e.getObject();
		if (newJobOffer != null) {
			to(JOB_OFFER).withParams(map("jobOfferId", newJobOffer.getId())).doGet();
		}
	}

	public void openContactDialog() {
		final Map<String, List<String>> params = new HashMap<>();
		final ArrayList<String> param = new ArrayList<>();
		param.add(String.valueOf(this.clientId));
		params.put("clientId", param);
		this.openDialog("contactDialog", params);
	}

	public void openDialog() {
		final Map<String, List<String>> params = new HashMap<>();
		final ArrayList<String> param = new ArrayList<>();
		param.add(String.valueOf(this.clientId));
		params.put("clientId", param);
		this.openDialog("clientDialog", params);
	}

	public void openJobOfferDialog() {
		final Map<String, List<String>> params = new HashMap<>();
		final ArrayList<String> param = new ArrayList<>();
		param.add(String.valueOf(this.clientId));
		params.put("clientId", param);
		this.openDialog("jobOfferDialog", params);
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setClientId(final Long clientId) {
		this.clientId = clientId;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}
}