package es.nivel36.laie.web.view.client.contact;

import java.util.Objects;

import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class ContactLazyDataModel extends AbstractLazyDataModel<Contact> {

	private static final long serialVersionUID = 1L;

	private transient ContactService contactService;

	public ContactLazyDataModel(final ContactService contactService) {
		Objects.requireNonNull(contactService, "ContactService can't be null");
		this.contactService = contactService;
	}

	@Override
	protected AbstractIndexedService<Contact> getService() {
		return this.contactService;
	}
}