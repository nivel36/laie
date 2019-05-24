package ged.web.view.client.contact;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddContactView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8611792798437280352L;

	private Client client;

	private Contact contact;

	@Inject
	private transient ContactService contactService;

	private Contact buildNewContact(final Client client) {
		final Contact newContact = new Contact();
		newContact.setClient(client);
		newContact.setPhoneNumber(client.getPhoneNumber());
		return newContact;
	}

	public Client getClient() {
		return this.client;
	}

	public Contact getContact() {
		return this.contact;
	}

	@PostConstruct
	public void init() {
		logger.debug("New contact");
		this.client = this.getValueFromFlash("client");
		this.contact = this.getValueFromFlash("contact");
		if (this.contact == null) {
			this.contact = this.buildNewContact(this.client);
		}
	}

	public void save() {
		this.contactService.save(this.contact);
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}