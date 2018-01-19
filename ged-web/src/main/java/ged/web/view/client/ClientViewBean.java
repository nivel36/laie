package ged.web.view.client;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.client.Contact;
import ged.ejb.core.Address;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.util.MessageUtils;
import ged.web.core.util.Navigate;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class ClientViewBean extends AbstractPageBean {

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

	public void editClient() {
		this.editable = true;
	}

	public void export() {

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

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.id == null) {
			Navigate.toPage("clientSearch");
		}
		long clientId = 0;
		try {
			clientId = Long.parseLong(this.id);
		} catch (final NumberFormatException ex) {
			Navigate.toPage("clientSearch");
		}
		this.client = this.clientService.find(clientId);
		if (this.client == null) {
			Navigate.toPage("clientSearch");
		}
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		this.jobOffers = this.jobOfferService.findAllJobOffersByClient(this.client);
		if (this.client.isDeleted()) {
			MessageUtils.addWarningMessage("message.erased_entity", "message.erased_entity");
		}
		this.contacts = new ArrayList<>(this.client.getContacts());
	}

	public boolean isEditable() {
		return this.editable;
	}

	public String modifyClient() {
		this.flash.put("client", this.client);
		return "clientEdit?faces-redirect=true";
	}

	public String newContact() {
		final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		final String url = req.getRequestURL().toString();
		this.flash.put("returnPage", url);

		return "editContact";
	}

	public void saveClient() {
		this.editable = false;
		this.client = this.clientService.save(this.client);
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

	public void undeleteClient() {
		this.clientService.undelete(this.client);
	}
}