package es.nivel36.laie.ejb.job.meeting;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.core.Subject;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;

import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;


public class MeetingDao extends AbstractDao {

	@Inject
	private SearchFacade searchFacade;
	
	public List<Meeting> findMeetingsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "Meeting.findByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}

	public List<Meeting> findMeetingByAttendeesEmail(final String email, final Page page) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(page);
		final String namedQuery = "Meeting.findByAttendeesEmail";
		final Parameters parameters = map("email", email);
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}

	public List<Meeting> findConductedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(page);
		final String namedQuery = "Meeting.findConductedByOwner";
		final Parameters parameters = map("owner", owner).and("now", LocalDateTime.now());
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}

	public List<Meeting> findPlannedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(page);
		final String namedQuery = "Meeting.findPlannedByOwner";
		final Parameters parameters = map("owner", owner).and("now", LocalDateTime.now());
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}

	public List<Subject> searchAteendees(final String query) {
		final List<User> users = searchFacade.search(User.class, Page.of(0, 3), null, query, "_email", "_name",
				"_surname");
		final List<Candidate> candidates = searchFacade.search(Candidate.class, Page.of(0, 3), null, query, "_email",
				"_name", "_surname");
		final List<Contact> contacts = searchFacade.search(Contact.class, Page.of(0, 3), null, query, "_email", "_name",
				"_surname");
		final List<Subject> atendees = new ArrayList<Subject>(users);
		atendees.addAll(candidates);
		atendees.addAll(contacts);
		return atendees;
	}
}