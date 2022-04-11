package es.nivel36.laie.web.view.meeting;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchMeetingView extends AbstractView {

	private static final long serialVersionUID = -8568862344770860565L;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchMeetingView.class);
	
	public static final String URL = "/meeting/search.xhtml";

	private List<Meeting> conductedMeetings;

	private List<Meeting> plannedMeetings;

	@Inject
	private transient MeetingService meetingService;

	@PostConstruct
	public void init() {
		logger.trace("Init search meeting");
		final User user = this.sessionUser.get();
		this.plannedMeetings = this.meetingService.findPlannedMeetings(user, Page.TEN_RESULTS_PER_PAGE);
		this.conductedMeetings = this.meetingService.findConductedMeetings(user, Page.TEN_RESULTS_PER_PAGE);
	}

	public List<Meeting> getConductedMeetings() {
		return this.conductedMeetings;
	}

	public List<Meeting> getPlannedMeetings() {
		return this.plannedMeetings;
	}

	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService, "MeetingService can't be null");
		this.meetingService = meetingService;
	}
}
