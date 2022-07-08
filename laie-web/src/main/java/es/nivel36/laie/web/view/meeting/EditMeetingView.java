package es.nivel36.laie.web.view.meeting;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.subject.Subject;
import es.nivel36.laie.ejb.core.subject.SubjectService;
import es.nivel36.laie.ejb.job.meeting.MeetingType;
import es.nivel36.laie.web.view.IndexView;

public class EditMeetingView extends AbstractMeetingView {

	private static final long serialVersionUID = -4650854396539154832L;

	private static final Logger logger = LoggerFactory.getLogger(EditMeetingView.class);

	public static final String URL = "/meeting/edit.xhtml";

	private transient @Inject SubjectService subjectService;

	@PostConstruct
	public void init() {
		logger.debug("EditMeetingView init");
		this.meetingTypes = this.initMeetingTypes();
		this.attendees = this.initAttendees();
		this.hours = this.initHours();
		this.durations = this.initDurations();
		this.meetingHour = this.initActualMeetingHour();
		this.meetingDate = LocalDate.now();
	}

	private List<MeetingType> initMeetingTypes() {
		return Arrays.asList(MeetingType.values());
	}

	private List<Subject> initAttendees() {
		final Set<String> emails = meeting.getAttendeesEmails();
		final List<Subject> attendeeList = new ArrayList<>();
		for (final String email : emails) {
			final Subject subject = subjectService.findByEmail(email);
			attendeeList.add(subject);
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
	
	public void delete() {
		this.meetingService.deleteMeeting(this.meeting);
		Faces.redirect(IndexView.URL);
	}
}
