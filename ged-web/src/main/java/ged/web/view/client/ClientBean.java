package ged.web.view.client;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
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
import ged.ejb.client.Contact;
import ged.ejb.core.Address;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	@Param(name = "clientId", required = true)
	private Client client;

	private List<Contact> contacts;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	public void editClient() {
		this.putValueToFlash("client", this.client);
	}

	public void export() {
	}

	public Client getClient() {
		return this.client;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	@PostConstruct
	public void init() {
		logger.trace("Init ClientBean");
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		this.contacts = new ArrayList<>(this.client.getContacts());
		this.jobOffers = this.jobOfferService.findJobOffersByClient(this.client);
	}

	public boolean isUserHasPermissionToEdit() {
		return sessionUser.hasPermissionToEdit(this.client);
	}

	public void newJobOffer() {
		final JobOffer newJobOffer = new JobOffer();
		newJobOffer.setClient(this.client);
		this.putValueToFlash("jobOffer", newJobOffer);
	}

	public void onCloseContactDialog(final SelectEvent e) {
		final Contact newContact = (Contact) e.getObject();
		if (newContact != null) {
			this.contacts.add(newContact);
		}
	}

	public void openContactDialog() {
		final Map<String, List<String>> params = this.buildDialogParameter("clientId", String.valueOf(this.client.getId()));
		this.openDialog("/faces/client/contactDialog", params);
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}