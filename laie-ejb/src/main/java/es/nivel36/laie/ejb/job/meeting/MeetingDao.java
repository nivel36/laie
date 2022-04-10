package es.nivel36.laie.ejb.job.meeting;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

@Repository
public class MeetingDao extends AbstractDao {

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
		final Parameters parameters = map("ownerId", owner).and("now", LocalDateTime.now());
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}
}