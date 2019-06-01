package ged.web.view.meeting;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.ejb.job.meeting.MeetingType;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddMeetingView extends AbstractView {

	private static final long serialVersionUID = 7690002057596615051L;

	private Meeting meeting;

	private transient MeetingService meetingService;

	private List<MeetingType> meetingTypes;

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
	}

	private Meeting initMeeting() {
		final Meeting newMeeting = new Meeting();
		newMeeting.addAttendee(this.sessionUser.get());
		return newMeeting;
	}

	private List<MeetingType> initMeetingTypes() {
		return Arrays.asList(MeetingType.values());
	}

	public void save() {
		this.meetingService.save(this.meeting);
	}

	public void setMeeting(final Meeting meeting) {
		this.meeting = meeting;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
