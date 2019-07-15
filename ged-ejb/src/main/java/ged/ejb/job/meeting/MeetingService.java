package ged.ejb.job.meeting;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.user.User;

@Stateless
public class MeetingService extends AbstractService<Meeting> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private MeetingDao meetingDao;

	public List<Meeting> findPlannedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		logger.debug("Find planned meetings by owner {}", owner);
		return this.meetingDao.findPlannedMeetings(owner, page);
	}
	
	public List<Meeting> findConductedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		logger.debug("Find conducted meetings by owner {}", owner);
		return this.meetingDao.findConductedMeetings(owner, page);
	}

	public List<Meeting> findMeetings(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		logger.debug("Find meetings by candidate {}", candidate);
		return this.meetingDao.findMeetingByAttendeesEmail(candidate.getEmail(), page);
	}

	public List<Meeting> findMeetings(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Find meetings by jobOffer {}", jobOffer);
		return this.meetingDao.findMeeting(jobOffer, page);
	}

	@Override
	protected AbstractDao<Meeting> getDao() {
		return this.meetingDao;
	}

	public void setJobMeetingDao(final MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}
}
