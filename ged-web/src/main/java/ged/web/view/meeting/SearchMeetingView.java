package ged.web.view.meeting;

import java.util.List;
import java.util.Objects;

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

	private static final long serialVersionUID = 1L;

	private List<Meeting> conductedMeetings;
	
	@Inject
	private transient MeetingService meetingService;

	private List<Meeting> plannedMeetings;

	public List<Meeting> getConductedMeetings() {
		return conductedMeetings;
	}

	public List<Meeting> getPlannedMeetings() {
		return plannedMeetings;
	}

	@PostConstruct
	public void init() {
		plannedMeetings = initPlannedMeetings();
		conductedMeetings = initConductedMeetings();
	}

	private List<Meeting> initConductedMeetings() {
		return meetingService.findConductedMeetings(sessionUser.get(), Page.of(0,10));
	}
	
	private List<Meeting> initPlannedMeetings() {
		return meetingService.findPlannedMeetings(sessionUser.get(), Page.of(0,10));
	}

	public void setMeetingService(MeetingService meetingService) {
		Objects.requireNonNull(meetingService, "MeetingService can't be null");
		this.meetingService = meetingService;
	}
}
