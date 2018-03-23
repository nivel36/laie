package ged.web.view.client;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.web.core.PageNotFoundException;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class ContactBean extends AbstractBean {

	private static final long serialVersionUID = 1978037803944619851L;

	private Contact contact;

	@Inject
	private transient ContactService contactService;

	private boolean editable;

	private String id;

	public void cancel() {
		this.editable = false;
	}

	public void edit() {
		this.editable = true;
	}

	public Contact getContact() {
		return this.contact;
	}

	public String getId() {
		return this.id;
	}

	public void init() {
		if (this.id == null) {
			throw new PageNotFoundException();
		}
		try {
			final long contactId = Long.parseLong(this.id);
			this.contact = this.contactService.find(contactId);
			if (this.contact == null) {
				throw new PageNotFoundException();
			}
		}
		catch (final NumberFormatException ex) {
			throw new PageNotFoundException();
		}
	}

	public boolean isEditable() {
		return this.editable;
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void update() {
		this.editable = false;
		this.contact = this.contactService.update(this.contact);
	}
}