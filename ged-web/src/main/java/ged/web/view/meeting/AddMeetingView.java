package ged.web.view.meeting;

import java.lang.invoke.MethodHandles;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.CandidateService;
import ged.ejb.client.ContactService;
import ged.ejb.core.model.Page;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.ejb.job.meeting.MeetingType;
import ged.ejb.person.Person;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddMeetingView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private Person attendee;

	private List<Person> attendees;

	@Inject
	private transient CandidateService candidateService;

	@Inject
	private transient ContactService contactService;

	private List<String> hours;

	private Meeting meeting;

	private LocalDate meetingDate;

	private String meetingHour;

	@Inject
	private transient MeetingService meetingService;

	private List<MeetingType> meetingTypes;

	@Inject
	private transient UserService userService;

	public void addAttendee() {
		if (this.attendee != null) {
			this.attendees.add(this.attendee);
		}
		this.attendee = null;
	}

	public Person getAttendee() {
		return this.attendee;
	}

	public List<Person> getAttendees() {
		return this.attendees;
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

	public String getMeetingHour() {
		return this.meetingHour;
	}

	public List<MeetingType> getMeetingTypes() {
		return this.meetingTypes;
	}

	@PostConstruct
	public void init() {
		this.meeting = this.initMeeting();
		this.meetingTypes = this.initMeetingTypes();
		this.attendees = this.initAttendees();
		this.hours = this.initHours();
		this.meetingHour = this.initActualMeetingHour();
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

	private List<Person> initAttendees() {
		final List<Person> attendeeList = new ArrayList<Person>();
		attendeeList.add(this.sessionUser.get());
		final Person person = this.getValueFromFlash("attendee");
		if (person != null) {
			attendeeList.add(person);
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

	private Meeting initMeeting() {
		final Meeting newMeeting = new Meeting();
		final User currentUser = this.sessionUser.get();
		newMeeting.setOwner(currentUser);
		return newMeeting;
	}

	private List<MeetingType> initMeetingTypes() {
		return Arrays.asList(MeetingType.values());
	}

	public void removeAttendee(final Person person) {
		this.attendees.remove(person);
	}

	public String save() {
		final LocalTime time = LocalTime.parse(this.meetingHour, DateTimeFormatter.ofPattern("H:mm"));
		final LocalDateTime meetingDateTime = LocalDateTime.of(this.meetingDate, time);
		this.meeting.setDatePlanned(meetingDateTime);
		for (final Person person : this.attendees) {
			this.meeting.addAttendee(person.getEmail());
		}
		this.meetingService.save(this.meeting);
		return PageEnum.MEETING_SEARCH.getUrl();
	}

	public List<Person> searchPerson(final String query) {
		logger.trace("Searching for person with the string {}", query);
		final List<Person> personsFound = new ArrayList<>();
		personsFound.addAll(this.candidateService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData());
		personsFound.addAll(this.userService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData());
		personsFound.addAll(this.contactService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData());
		return personsFound;
	}

	public void setAttendee(final Person attendee) {
		this.attendee = attendee;
	}

	public void setAttendees(final List<Person> attendees) {
		this.attendees = attendees;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setContactService(final ContactService contactService) {
		this.contactService = contactService;
	}

	public void setMeeting(final Meeting meeting) {
		this.meeting = meeting;
	}

	public void setMeetingDate(final LocalDate meetingDate) {
		this.meetingDate = meetingDate;
	}

	public void setMeetingHour(final String meetingHour) {
		this.meetingHour = meetingHour;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
