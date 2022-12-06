package es.nivel36.laie.web.view.client.contact;

import java.util.Objects;

import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = Contact.class)
public class ContactConverter extends AbstractConverter<Contact> {

	@Inject
	private ContactService contactService;

	public void setContactService(final ContactService contactService) {
		Objects.requireNonNull(contactService);
		this.contactService = contactService;
	}

	@Override
	protected Contact getAsObject(Long id) {
		return contactService.findContactById(id);
	}
}
