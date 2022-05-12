package es.nivel36.laie.web.view.meeting;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Contact;
import es.nivel36.laie.ejb.core.Subject;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.job.meeting.MeetingType;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddMeetingView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(AddMeetingView.class);

	public static final String URL = "/meeting/add.xhtml";

	private Subject attendee;

	private List<Subject> attendees;

	private List<String> durations;

	private List<String> hours;

	private Meeting meeting;

	private LocalDate meetingDate;

	private String meetingDuration;

	private String meetingHour;

	private List<MeetingType> meetingTypes;

	@Inject
	private transient MeetingService meetingService;

	@PostConstruct
	public void init() {
		this.meeting = this.initMeeting();
		this.meetingTypes = this.initMeetingTypes();
		this.attendees = this.initAttendees();
		this.hours = this.initHours();
		this.durations = this.initDurations();
		this.meetingHour = this.initActualMeetingHour();
	}

	private Meeting initMeeting() {
		final Meeting newMeeting = new Meeting();
		newMeeting.setOwner(this.sessionUser.get());
		return newMeeting;
	}

	private List<MeetingType> initMeetingTypes() {
		return Arrays.asList(MeetingType.values());
	}

	private List<Subject> initAttendees() {
		final List<Subject> attendeeList = new ArrayList<>();
		attendeeList.add(this.sessionUser.get());
		final Subject attendee = this.getValueFromFlash("attendee");
		if (attendee != null) {
			attendeeList.add(attendee);
		}
		return attendeeList;
	}

	private List<String> initHours() {
		final List<String> hourList = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			hourList.add(i + ":00");
			hourList.add(i + ":30");
		}
		return hourList;
	}

	private List<String> initDurations() {
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

	private String initActualMeetingHour() {
		final int hour = LocalTime.now().getHour();
		final int minute = LocalTime.now().getMinute();
		final StringBuilder sb = new StringBuilder();
		if (minute < 30) {
			sb.append(hour).append(":30");
		} else {
			if (hour < 23) {
				sb.append(hour + 1).append(":00");
			} else {
				sb.append("0:00");
			}
		}
		return sb.toString();
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

	public void save() {
		final LocalTime time = LocalTime.parse(this.meetingHour, DateTimeFormatter.ofPattern("H:mm"));
		final LocalTime endTime = LocalTime.parse(this.meetingDuration, DateTimeFormatter.ofPattern("H:mm"));
		final Duration duration = Duration.between(time, endTime);
		final LocalDateTime meetingDateTime = LocalDateTime.of(this.meetingDate, time);
		this.meeting.setDatePlanned(meetingDateTime);
		this.meeting.setDuration(duration);
		for (final Subject person : this.attendees) {
			this.meeting.addAttendee(person.getEmail());
		}
		this.meetingService.addMeeting(this.meeting);
		Faces.redirect(SearchMeetingView.URL);
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
