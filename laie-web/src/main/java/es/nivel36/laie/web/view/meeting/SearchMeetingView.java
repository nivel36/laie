package es.nivel36.laie.web.view.meeting;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class SearchMeetingView extends AbstractView {

	private static final long serialVersionUID = -8568862344770860565L;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchMeetingView.class);
	
	public static final String URL = "/meeting/search.xhtml";

	private List<Meeting> conductedMeetings;

	private List<Meeting> plannedMeetings;

	private transient @Inject MeetingService meetingService;

	@PostConstruct
	public void init() {
		logger.trace("Init search meeting");
		final User user = this.sessionUser.get();
		this.plannedMeetings = this.meetingService.findPlannedMeetings(user, Page.FIRST_TEN_RESULTS);
		this.conductedMeetings = this.meetingService.findConductedMeetings(user, Page.FIRST_TEN_RESULTS);
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
