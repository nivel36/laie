package ged.web.view.client.contact;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;

@FacesConverter(managed = true, forClass = Contact.class)
public class ContactConverter implements Converter<Contact> {

	@Inject
	private ContactService contactService;

	@Override
	public Contact getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.contactService.findByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Contact value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}
}
