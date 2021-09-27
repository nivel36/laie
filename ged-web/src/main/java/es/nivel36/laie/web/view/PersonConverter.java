package es.nivel36.laie.web.view;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactService;
import es.nivel36.laie.ejb.core.Subject;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

@FacesConverter(value = "personConverter", managed = true)
public class PersonConverter implements Converter<Subject> {

	@Inject
	private CandidateService candidateService;

	@Inject
	private ContactService contactService;

	@Inject
	private UserService userService;

	@Override
	public Subject getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		final User user = this.userService.findByUid(value);
		if (user != null) {
			return user;
		}
		final Candidate candidate = this.candidateService.findByUid(value);
		if (candidate != null) {
			return candidate;
		}
		final Contact contact = this.contactService.findByUid(value);
		if (contact != null) {
			return contact;
		}
		throw new IllegalArgumentException("converter value not found: " + value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Subject subject) {
		if (subject == null) {
			return null;
		}
		return subject.getUid();
	}

	public void setCandidateService(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService);
		this.candidateService = candidateService;
	}

	public void setContactService(final ContactService contactService) {
		Objects.requireNonNull(contactService);
		this.contactService = contactService;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}