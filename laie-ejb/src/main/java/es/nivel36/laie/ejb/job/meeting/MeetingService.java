package es.nivel36.laie.ejb.job.meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateDao;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.core.subject.SubjectDao;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class MeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MeetingService.class);

	private @Inject MeetingDao meetingDao;

	private @Inject UserDao userDao;

	private @Inject CandidateDao candidateDao;

	private @Inject ContactDao contactDao;

	private @Inject SubjectDao subjectDao;

	public void addMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		meetingDao.insert(meeting);
		for (final String email : meeting.getAttendeesEmails()) {
			final User user = userDao.findUserByEmail(email);
			if (user != null) {
				user.addMeeting(meeting);
				continue;
			}

			final Candidate candidate = candidateDao.findCandidateByEmail(email);
			if (candidate != null) {
				candidate.addMeeting(meeting);
				continue;
			}

			final Contact contact = contactDao.findContactByEmail(email);
			if (contact != null) {
				contact.addMeeting(meeting);
				continue;
			}
		}
	}

	public void deleteMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		for (final String email : meeting.getAttendeesEmails()) {
			final User user = userDao.findUserByEmail(email);
			if (user != null) {
				user.removeMeeting(meeting);
				continue;
			}

			final Candidate candidate = candidateDao.findCandidateByEmail(email);
			if (candidate != null) {
				candidate.removeMeeting(meeting);
				continue;
			}

			final Contact contact = contactDao.findContactByEmail(email);
			if (contact != null) {
				contact.removeMeeting(meeting);
				continue;
			}
		}
		this.meetingDao.delete(Meeting.class, meeting);
	}

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

	public List<Subject> searchPerson(final String query) {
		Objects.requireNonNull(query);
		if (query.length() < 3) {
			return new ArrayList<>();
		}
		return subjectDao.searchSubject(query);
	}

	public void setJobMeetingDao(final MeetingDao meetingDao) {
		Objects.requireNonNull(meetingDao);
		this.meetingDao = meetingDao;
	}

	public Meeting findMeetingById(final long id) {
		logger.debug("Find meeting by id {}", id);
		return this.meetingDao.findMeetingById(id);
	}

}
