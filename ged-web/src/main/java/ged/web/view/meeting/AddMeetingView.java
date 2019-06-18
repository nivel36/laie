package ged.web.view.meeting;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddMeetingView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 7690002057596615051L;

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
		if (attendee != null) {
			attendees.add(attendee);
		}
		attendee = null;
	}

	public Person getAttendee() {
		return attendee;
	}

	public List<Person> getAttendees() {
		return this.attendees;
	}

	public List<String> getHours() {
		return hours;
	}

	public Meeting getMeeting() {
		return this.meeting;
	}

	public LocalDate getMeetingDate() {
		return meetingDate;
	}

	public String getMeetingHour() {
		return meetingHour;
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
		String hour = String.valueOf(LocalTime.now().getHour());
		long minute =  LocalTime.now().getMinute();
		if(minute < 30) {
			return hour+":30";
		}
		else {
			return hour+":00";
		}
	}

	private List<Person> initAttendees() {
		final List<Person> attendeeList = new ArrayList<Person>();
		attendeeList.addAll(this.meeting.getUserAttendees());
		attendeeList.addAll(this.meeting.getContactAttendees());
		attendeeList.addAll(this.meeting.getCandidateAttendees());
		return attendeeList;
	}

	private List<String> initHours() {
		List<String> hourList = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			hourList.add(i + ":00");
			hourList.add(i + ":30");
		}
		return hourList;
	}

	private Meeting initMeeting() {
		final Meeting newMeeting = new Meeting();
		final User currentUser = this.sessionUser.get();
		newMeeting.addAttendee(currentUser);
		newMeeting.setOwner(currentUser);
		return newMeeting;
	}

	private List<MeetingType> initMeetingTypes() {
		return Arrays.asList(MeetingType.values());
	}

	public void removeAttendee(final Person person) {
		this.attendees.remove(person);
	}

	public void save() {
		LocalDateTime meetingDateTime = LocalDateTime.of(meetingDate, LocalTime.parse(meetingHour));
		this.meeting.setDatePlanned(meetingDateTime);
		this.meetingService.save(this.meeting);
	}

	public List<Person> searchPerson(final String query) {
		logger.trace("Searching for person with the string {}", query);
		final List<Person> personsFound = new ArrayList<>();
		personsFound.addAll(candidateService.search(query, Page.of(0, 10)).getResultData());
		personsFound.addAll(userService.search(query, Page.of(0, 10)).getResultData());
		personsFound.addAll(contactService.search(query, Page.of(0, 10)).getResultData());
		return personsFound;
	}

	public void setAttendee(Person attendee) {
		this.attendee = attendee;
	}

	public void setAttendees(final List<Person> attendees) {
		this.attendees = attendees;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public void setMeeting(final Meeting meeting) {
		this.meeting = meeting;
	}

	public void setMeetingDate(LocalDate meetingDate) {
		this.meetingDate = meetingDate;
	}

	public void setMeetingHour(String meetingHour) {
		this.meetingHour = meetingHour;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
