package ged.web.view.meeting;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Page;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.ejb.job.meeting.MeetingType;
import ged.ejb.person.Person;
import ged.ejb.person.PersonService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddMeetingView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 7690002057596615051L;

	private List<Person> attendees;

	private Person attendee;

	public Person getAttendee() {
		return attendee;
	}

	public void setAttendee(Person attendee) {
		this.attendee = attendee;
	}

	@Inject
	private transient PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private Meeting meeting;

	private transient MeetingService meetingService;

	private List<MeetingType> meetingTypes;

	public List<Person> getAttendees() {
		return this.attendees;
	}

	public void addAttendee() {
		attendees.add(attendee);
		attendee = null;
	}

	public Meeting getMeeting() {
		return this.meeting;
	}

	public List<MeetingType> getMeetingTypes() {
		return this.meetingTypes;
	}

	@PostConstruct
	public void init() {
		this.meeting = this.initMeeting();
		this.meetingTypes = this.initMeetingTypes();
		this.attendees = this.initAttendees();
	}

	private List<Person> initAttendees() {
		final List<Person> attendeeList = new ArrayList<Person>();
		attendeeList.addAll(this.meeting.getAttendees());
		return attendeeList;
	}

	private Meeting initMeeting() {
		final Meeting newMeeting = new Meeting();
		newMeeting.addAttendee(this.sessionUser.get());
		return newMeeting;
	}

	private List<MeetingType> initMeetingTypes() {
		return Arrays.asList(MeetingType.values());
	}

	public void removeAttendee(final Person person) {
		this.attendees.remove(person);
	}

	public void save() {
		this.meetingService.save(this.meeting);
	}

	public void setAttendees(final List<Person> attendees) {
		this.attendees = attendees;
	}

	public void setMeeting(final Meeting meeting) {
		this.meeting = meeting;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public List<Person> searchPerson(final String query) {
		logger.trace("Searching for person with the string {}", query);
		final List<Person> persons = this.personService.search(query, Page.ALL).getResultData();
		persons.removeAll(attendees);
		return persons;
	}
}
