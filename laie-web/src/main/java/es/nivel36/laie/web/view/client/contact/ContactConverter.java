package es.nivel36.laie.web.view.client.contact;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.client.ContactDto;
import es.nivel36.laie.ejb.client.ContactService;

@FacesConverter(managed = true, forClass = ContactDto.class)
public class ContactConverter implements Converter<ContactDto> {

	@Inject
	private ContactService contactService;

	@Override
	public ContactDto getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.contactService.findContactByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final ContactDto value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setContactService(final ContactService contactService) {
		Objects.requireNonNull(contactService);
		this.contactService = contactService;
	}
}
