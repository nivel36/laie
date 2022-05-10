package es.nivel36.laie.web.core.view;

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

@FacesConverter(managed = true, forClass = Subject.class)
public class SubjectConverter implements Converter<Subject> {
	
	@Inject
	private UserService service;

	@Inject
	private CandidateService candidateService;

	@Inject
	private ContactService contactService;

	@Override
	public Subject getAsObject(FacesContext context, UIComponent component, String email) {
		final User user = service.findUserByEmail(email);
		if (user != null) {
			return user;
		}

		final Candidate candidate = candidateService.findCandidateByEmail(email);
		if (candidate != null) {
			return candidate;
		}

		final Contact contact = contactService.findContactByEmail(email);
		if (contact != null) {
			return contact;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Subject value) {
		return value.getEmail();		
	}
}
