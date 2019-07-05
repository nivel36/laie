package ged.web.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.client.Contact;
import ged.ejb.client.ContactService;
import ged.ejb.person.Person;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@FacesConverter(value = "personConverter", managed = true)
public class PersonConverter implements Converter<Person> {

	@Inject
	private CandidateService candidateService;

	@Inject
	private ContactService contactService;

	@Inject
	private UserService userService;

	@Override
	public Person getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			int separator = value.indexOf("::");
			if (separator == -1) {
				throw new ConverterException(value + " is not a valid id");
			}
			String type = value.substring(0, separator);
			String idString = value.substring(separator + 2, value.length());
			final long id = Long.parseLong(idString);
			if ("user".equals(type)) {
				return this.userService.find(id);
			} else if ("candidate".equals(type)) {
				return this.candidateService.find(id);
			} else if ("contact".equals(type)) {
				return this.contactService.find(id);
			}
		} catch (final NumberFormatException e) {
			throw new ConverterException(value + " is not a valid id");
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Person person) {
		if (person == null) {
			return null;
		} else {
			String id = String.valueOf(person.getId());
			if (person instanceof User) {
				return "user::" + id;
			} else if (person instanceof Contact) {
				return "contact::" + id;
			} else if (person instanceof Candidate) {
				return "candidate::" + id;
			} else {
				throw new ConverterException("Unknow entity");
			}
		}
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}