package ged.web.view.client;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.CLIENT;
import static ged.web.core.util.Page.CLIENT_SEARCH;
import static ged.web.core.util.Page.JOB_OFFER_EDIT;
import static ged.web.core.util.Page.JOB_OFFER;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.client.Contact;
import ged.ejb.core.Address;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.util.Message;
import ged.web.core.util.Navigate;
import ged.web.core.util.Page;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientBean extends AbstractBean {

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	private List<Contact> contacts;

	private boolean editable;

	private String id;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private JobOffer selectedJobOffer;

	public Client buildNewClient() {
		final Client newClient = new Client();
		newClient.setOwner(this.sessionBean.getUser());
		newClient.setAddress(new Address());
		return newClient;
	}

	public void cancelEdit() {
		if (this.client.getId() == 0) {
			Navigate.to(Page.CLIENT_SEARCH).doPost();
		} else {
			this.editable = false;
		}
	}

	public void editClient() {
		this.editable = true;
	}

	public void error() {
		to(CLIENT_SEARCH).doPost();
	}

	public void export() {
		// TODO
	}

	public Client getClient() {
		return this.client;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public String getId() {
		return this.id;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public JobOffer getSelectedJobOffer() {
		return this.selectedJobOffer;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.id == null) {
			this.client = buildNewClient();
			this.editable = true;
		} else {
			long clientId = 0;
			try {
				clientId = Long.parseLong(this.id);
			} catch (final NumberFormatException ex) {
				error();
			}
			this.client = this.clientService.find(clientId);
			if (this.client == null) {
				error();
			}
			if (this.client.getAddress() == null) {
				this.client.setAddress(new Address());
			}
			this.jobOffers = this.jobOfferService.findAllJobOffersByClient(this.client);
			if (this.client.isDeleted()) {
				Message.addWarning("message.erased_entity", "message.erased_entity");
			}
			this.contacts = new ArrayList<>(this.client.getContacts());
		}
	}

	public void insertClient() {
		this.editable = false;
		this.clientService.insert(this.client);
		Navigate.to(CLIENT).withParams(map("id", this.client.getId())).doGet();
	}

	public boolean isEditable() {
		return this.editable;
	}

	public boolean isInsertable() {
		return this.client.getId() == 0;
	}

	public String modifyClient() {
		this.flash.put("client", this.client);
		return to(CLIENT).toUrl();
	}

	public String newContact() {
		final String url = "/faces/client/clientView?id=" + this.id;
		this.flash.put("returnPage", url);
		return "editContact";
	}

	public String newJobOffer() {
		final JobOffer jobOffer = new JobOffer();
		jobOffer.setClient(this.client);
		final String url = to(CLIENT).withParams(map("id", this.id)).toUrl();
		this.flash.put("returnPage", url);
		this.flash.put("jobOffer", jobOffer);
		return to(JOB_OFFER_EDIT).toUrl();
	}

	public void onJobOfferSelect() {
		to(JOB_OFFER).withParams(map("id", this.selectedJobOffer.getId())).doGet();
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setContacts(final List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setSelectedJobOffer(final JobOffer selectedJobOffer) {
		this.selectedJobOffer = selectedJobOffer;
	}

	public void undeleteClient() {
		this.clientService.undelete(this.client);
	}

	public void updateClient() {
		this.editable = false;
		this.client = this.clientService.update(this.client);
	}
}