package ged.ejb.job.meeting;

import static ged.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;

@Repository
public class MeetingDao extends AbstractDao<Meeting> {
	
	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.findByQuery(JobOffer.class, "Meeting.findByUid", map("uid", uid));
	}

	public List<Meeting> findConductedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(page);
		return this.findByQuery(Meeting.class, "Meeting.findConductedByOwner", map("owner", owner), page);
	}
	
	public List<Meeting> findMeeting(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		return this.findByQuery(Meeting.class, "Meeting.findByJobOffer", map("jobOffer", jobOffer), page);
	}

	public List<Meeting> findMeetingByAttendeesEmail(final String email, final Page page) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(page);
		return this.findByQuery(Meeting.class, "Meeting.findByAttendeesEmail", map("email", email), page);
	}

	public List<Meeting> findPlannedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		Objects.requireNonNull(page);
		return this.findByQuery(Meeting.class, "Meeting.findPlannedByOwner", map("owner", owner), page);
	}

	@Override
	protected Class<Meeting> getType() {
		return Meeting.class;
	}

	@Override
	public String[] searchFields() {
		return new String[] {};
	}
}