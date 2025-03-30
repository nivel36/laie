package es.nivel36.laie.ejb.core.subject;

import java.util.ArrayList;
import java.util.List;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.user.User;
import jakarta.inject.Inject;

public class SubjectDao extends AbstractDao {

	private @Inject SearchFacade searchFacade;

	public List<Subject> searchSubject(final String query) {
		final List<User> users = searchFacade.search(User.class, Page.of(0, 3), null, query, "_email", "_name",
				"_surname");
		final List<Candidate> candidates = searchFacade.search(Candidate.class, Page.of(0, 3), null, query, "_email",
				"_name", "_surname");
		final List<Contact> contacts = searchFacade.search(Contact.class, Page.of(0, 3), null, query, "_email", "_name",
				"_surname");
		final List<Subject> atendees = new ArrayList<>(users);
		atendees.addAll(candidates);
		atendees.addAll(contacts);
		return atendees;
	}

}
