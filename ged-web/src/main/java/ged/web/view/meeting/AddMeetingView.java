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
		return meeting;
	}
	
	public void save() {
		meetingService.save(meeting);
	}

	public List<MeetingType> getMeetingTypes() {
		return meetingTypes;
	}

	@PostConstruct
	public void init() {
		this.meeting = initMeeting();
		this.meetingTypes = initMeetingTypes();
	}

	private Meeting initMeeting() {
		Meeting newMeeting = new Meeting();
		return newMeeting;
	}

	private List<MeetingType> initMeetingTypes() {
		return Arrays.asList(MeetingType.values());
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
