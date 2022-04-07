package es.nivel36.laie.ejb.job.meeting;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.job.offer.JobOfferDao;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class MeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private MeetingDao meetingDao;

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	@Repository
	private UserDao userDao;

	@Inject
	@Repository
	private JobOfferDao jobOfferDao;

	private MeetingMapper meetingMapper;

	private MeetingMerger meetingMerger;

	@PostConstruct
	public void init() {
		meetingMapper = new MeetingMapper();
		meetingMerger = new MeetingMerger(jobOfferDao, userDao);
	}

	public void createMeeting(final MeetingDto meeting) {
		final Meeting entity = new Meeting();
		this.meetingMerger.merge(entity, meeting);
		this.meetingDao.insert(entity);
	}

	public void updateMeeting(final MeetingDto meeting) {
		Objects.requireNonNull(meeting);
		final Meeting entity = this.meetingDao.findByUid(meeting.getUid());
		this.meetingMerger.merge(entity, meeting);
	}

	public MeetingDto findByUid(final String uid) {
		Objects.requireNonNull(uid);
		final Meeting meeting = this.meetingDao.findByUid(uid);
		return meetingMapper.map(meeting);
	}

	public List<MeetingDto> findConductedMeetings(final String ownerUid, final Page page) {
		Objects.requireNonNull(ownerUid);
		logger.debug("Find conducted meetings by owner {}", ownerUid);
		final List<Meeting> meetings = this.meetingDao.findConductedMeetings(ownerUid, page);
		return meetingMapper.mapList(meetings);
	}

	public List<MeetingDto> findMeetingsByCandidate(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		logger.debug("Find meetings by candidate {}", candidateUid);
		final Candidate candidate = this.candidateDao.findByUid(candidateUid);
		final List<Meeting> meetings = this.meetingDao.findMeetingByAttendeesEmail(candidate.getEmail(), page);
		return meetingMapper.mapList(meetings);
	}

	public List<MeetingDto> findMeetingsByJobOffer(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		logger.debug("Find meetings by jobOffer {}", jobOfferUid);
		final List<Meeting> meetings = this.meetingDao.findMeetingsByJobOffer(jobOfferUid, page);
		return meetingMapper.mapList(meetings);
	}

	public List<MeetingDto> findPlannedMeetings(final String ownerId, final Page page) {
		Objects.requireNonNull(ownerId);
		logger.debug("Find planned meetings by owner {}", ownerId);
		final List<Meeting> meetings = this.meetingDao.findPlannedMeetings(ownerId, page);
		return meetingMapper.mapList(meetings);
	}

	public void setJobMeetingDao(final MeetingDao meetingDao) {
		Objects.requireNonNull(meetingDao);
		this.meetingDao = meetingDao;
	}

	public void setCanidateDao(final CandidateDao candidateDao) {
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
	}
}
