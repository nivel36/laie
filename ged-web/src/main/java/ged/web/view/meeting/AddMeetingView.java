package ged.web.view.meeting;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ged.ejb.job.meeting.Meeting;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class AddMeetingView extends AbstractView {

	private static final long serialVersionUID = 7690002057596615051L;
	
	private Meeting meeting;
	
	@PostConstruct
	public void init() {
		this.meeting = initMeeting();
	}
	
	private Meeting initMeeting() {
		Meeting newMeeting = new Meeting();
		return newMeeting;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
}
