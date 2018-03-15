package ged.web.view.client;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;
import static ged.web.core.util.Page.CLIENT;

import java.lang.invoke.MethodHandles;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.client.Client;
import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class ContactDialogBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8611792798437280352L;

	private Contact contact;

	@Inject
	private transient ContactService contactService;

	private Contact buildContact() {
		final Client client = this.getAttribute("client");
		if (client == null) {
			throw new IllegalStateException("Null client");
		}
		final Contact newContact = new Contact();
		newContact.setClient(client);
		newContact.setPhoneNumber(client.getPhoneNumber());
		return newContact;
	}

	@Override
	protected void dispose() {
		logger.trace("ContactDialog closed");
		this.contact = null;
	}

	public Contact getContact() {
		return this.contact;
	}

	@Override
	protected void init() {
		logger.trace("ContactDialog oppened");
		this.contact = this.getAttribute("contact");
		if (this.contact == null) {
			this.contact = this.buildContact();
		}
	}

	public void save() {
		logger.debug("ContactDialog save action performed");
		this.contactService.insert(this.contact);
		to(CLIENT).withParams(map("id", this.contact.getClient().getId())).doGet();
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}