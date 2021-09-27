package es.nivel36.laie.web.view.meeting;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchMeetingView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private List<Meeting> conductedMeetings;

	@Inject
	private transient MeetingService meetingService;

	private List<Meeting> plannedMeetings;

	public List<Meeting> getConductedMeetings() {
		return this.conductedMeetings;
	}

	public List<Meeting> getPlannedMeetings() {
		return this.plannedMeetings;
	}

	@PostConstruct
	public void init() {
		this.plannedMeetings = this.initPlannedMeetings();
		this.conductedMeetings = this.initConductedMeetings();
	}

	private List<Meeting> initConductedMeetings() {
		return this.meetingService.findConductedMeetings(this.sessionUser.get(), Page.TEN_RESULTS_PER_PAGE);
	}

	private List<Meeting> initPlannedMeetings() {
		return this.meetingService.findPlannedMeetings(this.sessionUser.get(), Page.TEN_RESULTS_PER_PAGE);
	}

	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService, "MeetingService can't be null");
		this.meetingService = meetingService;
	}
}
