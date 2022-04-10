package es.nivel36.laie.ejb.job.meeting;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferDao;
import es.nivel36.laie.ejb.user.User;

@Stateless
public class MeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MeetingService.class);

	@Inject
	@Repository
	private MeetingDao meetingDao;

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	public List<Meeting> findConductedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		logger.debug("Find conducted meetings by owner {}", owner);
		return this.meetingDao.findConductedMeetings(owner, page);
	}

	public List<Meeting> findMeetingsByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		logger.debug("Find meetings by candidate {}", candidate);
		return this.meetingDao.findMeetingByAttendeesEmail(candidate.getEmail(), page);
	}

	public List<Meeting> findMeetingsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Find meetings by jobOffer {}", jobOffer);
		return this.meetingDao.findMeetingsByJobOffer(jobOffer, page);
	}

	public List<Meeting> findPlannedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		logger.debug("Find planned meetings by owner {}", owner);
		return this.meetingDao.findPlannedMeetings(owner, page);
	}

	public void setJobMeetingDao(final MeetingDao meetingDao) {
		Objects.requireNonNull(meetingDao);
		this.meetingDao = meetingDao;
	}
}
