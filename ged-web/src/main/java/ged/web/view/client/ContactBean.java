package ged.web.view.client;

import javax.inject.Inject;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.web.core.view.AbstractBean;

public class ContactBean extends AbstractBean {

	private static final long serialVersionUID = 1978037803944619851L;

	private Contact contact;

	@Inject
	private transient ContactService contactService;

	private String id;

	public void error() {

	}

	public Contact getContact() {
		return this.contact;
	}

	public void init() {
		if (this.id == null) {
			this.error();
		}
		try {
			final long contactId = Long.parseLong(this.id);
			this.contact = this.contactService.find(contactId);
			if (this.contact == null) {
				this.error();
			}
		} catch (final NumberFormatException ex) {
			this.error();
		}
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}
}