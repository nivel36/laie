package ged.web.view.client.contact;

import java.util.Objects;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.ejb.core.AbstractService;
import ged.web.core.view.AbstractLazyDataModel;

public class ContactLazyDataModel extends AbstractLazyDataModel<Contact> {

	private static final long serialVersionUID = 1L;

	private transient ContactService contactService;

	public ContactLazyDataModel(final ContactService contactService) {
		Objects.requireNonNull(contactService, "ContactService can't be null");
		this.contactService = contactService;
	}

	@Override
	protected AbstractService<Contact> getService() {
		return this.contactService;
	}
}