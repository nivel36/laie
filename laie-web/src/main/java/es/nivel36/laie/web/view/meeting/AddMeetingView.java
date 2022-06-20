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
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingType;
import es.nivel36.laie.web.view.IndexView;

@Named
@ViewScoped
public class AddMeetingView extends AbstractMeetingView {

	private static final long serialVersionUID = 5263318378326323734L;

	private static final Logger logger = LoggerFactory.getLogger(AddMeetingView.class);

	public static final String URL = "/meeting/add.xhtml";

	@PostConstruct
	public void init() {
		logger.debug("AddMeetingView init");
		this.meeting = this.initMeeting();
		this.meetingTypes = this.initMeetingTypes();
		this.attendees = this.initAttendees();
		this.hours = this.initHours();
		this.durations = this.initDurations();
		this.meetingHour = this.initActualMeetingHour();
		this.meetingDate = LocalDate.now();
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
		Faces.redirect(IndexView.URL);
	}
}
