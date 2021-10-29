package es.nivel36.laie.ejb.job.meeting;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.UidGenerator;
import es.nivel36.laie.ejb.core.util.Parameters;

@Repository
public class MeetingDao extends AbstractDao {

	public void insert(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		this.setUid(meeting);
		this.em.persist(meeting);
	}

	private void setUid(Meeting meeting) {
		String uid;
		do {
			uid = UidGenerator.generate(Meeting.class);
			meeting.setUid(uid);
		} while (!this.checkDuplicateUid(uid));
	}

	private boolean checkDuplicateUid(final String uid) {
		final String namedQuery = "Meeting.checkDuplicateUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public Meeting findByUid(final String uid) {
		Objects.requireNonNull(uid);
		final String namedQuery = "Meeting.findByUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(Meeting.class, namedQuery, parameters);
	}

	public List<Meeting> findMeetingsByJobOffer(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(page);
		final String namedQuery = "Meeting.findByJobOffer";
		final Parameters parameters = map("jobOfferUid", jobOfferUid);
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}

	public List<Meeting> findMeetingByAttendeesEmail(final String email, final Page page) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(page);
		final String namedQuery = "Meeting.findByCandidate";
		final Parameters parameters = map("email", email);
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}

	public List<Meeting> findConductedMeetings(final String ownerUid, final Page page) {
		Objects.requireNonNull(ownerUid);
		Objects.requireNonNull(page);
		final String namedQuery = "Meeting.findConductedByOwner";
		final Parameters parameters = map("ownerUid", ownerUid);
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}

	public List<Meeting> findPlannedMeetings(final String ownerUid, final Page page) {
		Objects.requireNonNull(ownerUid);
		Objects.requireNonNull(page);
		final String namedQuery = "Meeting.findPlannedByOwner";
		final Parameters parameters = map("ownerUid", ownerUid);
		return this.findByQuery(Meeting.class, namedQuery, parameters, page);
	}
}