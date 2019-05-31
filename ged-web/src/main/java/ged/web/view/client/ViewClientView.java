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
import ged.ejb.core.model.Address;
import ged.ejb.core.model.Page;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.Message;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ViewClientView extends AbstractView {

	private static final String CLIENT_KEY = "client";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	@Inject
	@Param(name = "id", required = true)
	private Client client;

	private List<Contact> contacts;

	private boolean editable;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	public String editClient() {
		logger.debug("Edit client action performed");
		this.putValueToFlash(CLIENT_KEY, this.client);
		return PageEnum.CLIENT_EDIT.getUrl();
	}

	public void export() throws IOException {
		logger.debug("Export client action performed");
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
		if (this.client == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Client {} init", this.client);
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		this.contacts = new ArrayList<>(this.client.getContacts());
		this.jobOffers = this.jobOfferService.findJobOffers(this.client, Page.ALL);
		checkDeleted();
		this.editable = this.sessionUser.hasPermissionToEdit(this.client);
	}

	private void checkDeleted() {
		if (this.client.isDeleted()) {
			logger.warn("Client is deleted");
			Message.addWarning("message.erased_entity", "message.erased_entity");
		}
	}

	public boolean isEditable() {
		return this.editable;
	}

	public void newContact() {
		logger.debug("New contact action performed");
		this.putValueToFlash(CLIENT_KEY, this.client);
	}

	public void newJobOffer() {
		logger.debug("New job offer action performed");
		this.putValueToFlash(CLIENT_KEY, this.client);
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}