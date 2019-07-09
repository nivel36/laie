package ged.web.view.meeting;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.model.Page;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchMeetingView extends AbstractView {

	private static final long serialVersionUID = 1105875525922748690L;

	private List<Meeting> meetings;

	@Inject
	private transient MeetingService meetingService;

	public List<Meeting> getMeetings() {
		return meetings;
	}

	@PostConstruct
	public void init() {
		meetings = initMeetings();
	}

	private List<Meeting> initMeetings() {
		return meetingService.findMeeting(sessionUser.get(), Page.ALL);
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
