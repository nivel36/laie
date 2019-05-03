package ged.web.view.client;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
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
public class ViewClientBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	@Inject
	@Param(name = "id", required = true)
	private Client client;

	private List<Contact> contacts;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	public void editClient() {
		logger.debug("Edit client action performed");
		this.putValueToFlash("client", this.client);
	}

	public void export() throws IOException {
		logger.debug("Export clients action performed");
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
		logger.trace("Client {} init", this.client);
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		this.contacts = new ArrayList<>(this.client.getContacts());
		this.jobOffers = this.jobOfferService.findJobOffersByClient(this.client);
	}

	public boolean isEditable() {
		return this.sessionUser.hasPermissionToEdit(this.client);
	}

	public void newContact() {
		this.putValueToFlash("client", this.client);
	}

	public void newJobOffer() {
		logger.debug("New job offer action performed");
		this.putValueToFlash("client", this.client);
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}