package ged.web.view.client;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.ejb.core.Address;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.CloseDialogListener;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ClientBean extends AbstractBean implements CloseDialogListener {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1412905869664752048L;

	private Client client;

	@Inject
	private transient ClientService clientService;

	private List<Contact> contacts;

	@Inject
	private transient ContactService contactService;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	public void export() {
	}

	private Client findClient(final Long clientId) {
		this.client = this.clientService.find(clientId);
		if (this.client == null) {
			logger.error("Client not found");
			throw new PageNotFoundException();
		}
		if (this.client.getAddress() == null) {
			this.client.setAddress(new Address());
		}
		return this.client;
	}

	private Long getClienIdFromGetParameter() {
		try {
			final String clientIdValue = this.getValueFromGetParameters("clientId");
			if (clientIdValue == null) {
				logger.error("ClientId is null");
				throw new PageNotFoundException();
			}
			return Long.parseLong(clientIdValue);
		}
		catch (final NumberFormatException e) {
			logger.error("ClientId is not a number");
			throw new PageNotFoundException();
		}
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
		final Long clientId = this.getClienIdFromGetParameter();
		this.client = this.findClient(clientId);
		this.jobOffers = this.jobOfferService.findJobOffersByClient(this.client);
		this.contacts = this.contactService.findContactsByClient(this.client);
	}

	public boolean isUserHasPermissionToEdit() {
		return this.userHasPermissionToEdit(this.client);
	}

	@Override
	public void onCloseDialog(final Object value) {
		this.client = (Client) value;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}