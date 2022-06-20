package es.nivel36.laie.web.view.meeting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.job.meeting.MeetingType;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;

public class AbstractMeetingView extends AbstractView {

	private static final long serialVersionUID = -3589921939264190541L;

	private static final Logger logger = LoggerFactory.getLogger(AbstractMeetingView.class);

	protected Subject attendee;

	protected List<Subject> attendees;

	protected List<String> durations;

	protected List<String> hours;

	protected @Param Meeting meeting;

	protected LocalDate meetingDate;

	protected String meetingDuration;

	protected String meetingHour;

	protected List<MeetingType> meetingTypes;

	protected transient @Inject MeetingService meetingService;

	protected List<String> initHours() {
		final List<String> hourList = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			hourList.add(i + ":00");
			hourList.add(i + ":30");
		}
		return hourList;
	}

	protected List<String> initDurations() {
		final List<String> durations = new ArrayList<>();
		durations.add("0:15");
		durations.add("0:30");
		durations.add("0:45");
		for (int i = 1; i < 3; i++) {
			durations.add(i + ":00");
			durations.add(i + ":15");
			durations.add(i + ":30");
			durations.add(i + ":45");
		}
		return durations;
	}

	public void addAttendee() {
		if (this.attendee != null) {
			this.attendees.add(this.attendee);
		}
		this.attendee = null;
	}

	public boolean isAvaliable(final User subject) {
		return true;
	}

	public boolean isAvaliable(final Candidate subject) {
		return true;
	}

	public boolean isAvaliable(final Contact subject) {
		return true;
	}

	public void removeAttendee(final Subject person) {
		this.attendees.remove(person);
	}

	public List<Subject> searchPerson(final String query) {
		logger.trace("Searching for person with the string {}", query);
		return meetingService.searchPerson(query);
	}

	public Subject getAttendee() {
		return this.attendee;
	}

	public List<Subject> getAttendees() {
		return this.attendees;
	}

	public List<String> getDurations() {
		return this.durations;
	}

	public List<String> getHours() {
		return this.hours;
	}

	public Meeting getMeeting() {
		return this.meeting;
	}

	public LocalDate getMeetingDate() {
		return this.meetingDate;
	}

	public String getMeetingDuration() {
		return this.meetingDuration;
	}

	public String getMeetingHour() {
		return this.meetingHour;
	}

	public List<MeetingType> getMeetingTypes() {
		return this.meetingTypes;
	}

	public void setAttendee(final Subject attendee) {
		this.attendee = attendee;
	}

	public void setAttendees(final List<Subject> attendees) {
		this.attendees = attendees;
	}

	public void setMeeting(final Meeting meeting) {
		this.meeting = meeting;
	}

	public void setMeetingDate(final LocalDate meetingDate) {
		this.meetingDate = meetingDate;
	}

	public void setMeetingDuration(final String meetingDuration) {
		this.meetingDuration = meetingDuration;
	}

	public void setMeetingHour(final String meetingHour) {
		this.meetingHour = meetingHour;
	}
}
