package ged.web.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
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
		User user = userService.findUserByEmail(value);
		if (user != null) {
			return user;
		}
		Candidate candidate = candidateService.findCandidateByEmail(value);
		if (candidate != null) {
			return candidate;
		}
		Contact contact = contactService.findContactByEmail(value);
		if (contact != null) {
			return contact;
		}
		return new SimplePerson(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Person person) {
		if (person == null) {
			return null;
		}
		return person.getEmail();
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