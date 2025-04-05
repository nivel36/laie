package es.nivel36.laie.ejb.job.meeting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateDao;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.client.ContactDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class MeetingService {

	private static final Logger logger = LoggerFactory.getLogger(MeetingService.class);

	private @Inject MeetingDao meetingDao;
	private @Inject UserDao userDao;
	private @Inject CandidateDao candidateDao;
	private @Inject ContactDao contactDao;

	public void addMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		logger.debug("Adding new meeting {}", meeting);
		meetingDao.insert(meeting);
		addMeetingsInPersons(meeting);
	}
	
	public Meeting findMeetingById(final long meetingId) {
		logger.debug("Finding meeting by id {}", meetingId);
		return this.meetingDao.find(Meeting.class, meetingId);
	}

	private void addMeetingsInPersons(final Meeting meeting) {
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
		logger.debug("Deleting meeting {}", meeting);
		deleteMeetingsInPersons(meeting);
		this.meetingDao.delete(Meeting.class, meeting);
	}

	private void deleteMeetingsInPersons(final Meeting meeting) {
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
	}

	public Meeting updateMeeting(final Meeting meeting) {
		Objects.requireNonNull(meeting);
		logger.debug("Updating meeting {}", meeting);
		final Meeting savedMeeting = meetingDao.find(Meeting.class, meeting.getId());
		final Meeting updatedMeeting = meetingDao.update(meeting);
		this.deleteMeetingsInPersons(savedMeeting);
		this.addMeetingsInPersons(updatedMeeting);
		return updatedMeeting;
	}

	public List<Meeting> findConductedMeetings(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		logger.debug("Finding conducted meetings by owner {}", owner);
		return this.meetingDao.findConductedMeetings(owner, page);
	}

	public List<Meeting> findMeetingsByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		logger.debug("Finding meetings by candidate {}", candidate);
		return this.meetingDao.findByAttendeesEmail(candidate.getEmail(), page);
	}

	public List<Meeting> findFutureMeetingsByOwner(final User owner, final Page page) {
		Objects.requireNonNull(owner);
		logger.debug("Finding planned meetings by owner {}", owner);
		return this.meetingDao.findFutureMeetings(owner, page);
	}

	public List<Meeting> findMonthMeetingsByUser(final User user, final LocalDateTime date) {
		Objects.requireNonNull(user);
		logger.debug("Finding planned meetings by user {} and year {}/month {}", user, date.getYear(), date.getMonth());
		return this.meetingDao.findMonthMeetingsByUser(user, date);
	}

	public long countFutureMeetingsByOwner(final User owner) {
		Objects.requireNonNull(owner);
		logger.debug("Counting planned meetings by owner {}", owner);
		return this.meetingDao.countFutureMeetings(owner);
	}

	public void setMeetingDao(final MeetingDao meetingDao) {
		this.meetingDao = Objects.requireNonNull(meetingDao);
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = Objects.requireNonNull(userDao);
	}

	public void setCandidateDao(final CandidateDao candidateDao) {
		this.candidateDao = Objects.requireNonNull(candidateDao);
	}

	public void setContactDao(final ContactDao contactDao) {
		this.contactDao = Objects.requireNonNull(contactDao);
	}
}
