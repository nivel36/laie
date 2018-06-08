package ged.web.view.client;

import java.lang.invoke.MethodHandles;

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
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class ContactDialogBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8611792798437280352L;

	@Inject
	private transient ClientService clientService;

	private Contact contact;

	@Inject
	private transient ContactService contactService;

	private Contact buildContact() {
		final Long clientId = this.getIdFromParameters("clientId");
		final Client client = this.clientService.find(clientId);
		if (client == null) {
			logger.error("Null client");
			throw new IllegalStateException("Null client");
		}
		final Contact newContact = new Contact();
		newContact.setClient(client);
		newContact.setPhoneNumber(client.getPhoneNumber());
		return newContact;
	}

	public Contact getContact() {
		return this.contact;
	}

	@PostConstruct
	public void init() {
		logger.trace("ContactDialog oppened");
		final Long contactId = this.getIdFromParameters("contactId");
		if (contactId != null) {
			this.contact = this.contactService.find(contactId);
			if (this.contact == null) {
				logger.error("Null contact");
				throw new IllegalStateException("Null contact");
			}
		}
		else {
			this.contact = this.buildContact();
		}
	}

	public boolean isNewContact() {
		return this.contact.getId() == 0;
	}

	public void save() {
		logger.debug("ContactDialog save action performed");
		this.contactService.insert(this.contact);
		this.closeDialog(this.contact);
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}