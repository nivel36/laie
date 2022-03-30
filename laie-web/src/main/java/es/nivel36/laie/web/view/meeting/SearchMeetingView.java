package es.nivel36.laie.web.view.meeting;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.MeetingDto;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchMeetingView extends AbstractView {

	private static final long serialVersionUID = -8568862344770860565L;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchMeetingView.class);
	
	public static final String URL = "/meeting/search.xhtml";

	private List<MeetingDto> conductedMeetings;

	private List<MeetingDto> plannedMeetings;

	@Inject
	private transient MeetingService meetingService;

	@PostConstruct
	public void init() {
		logger.trace("Init search meeting");
		final UserDto user = this.sessionUser.get();
		final String userUid = user.getUid();
		this.plannedMeetings = this.initPlannedMeetings(userUid);
		this.conductedMeetings = this.initConductedMeetings(userUid);
	}

	private List<MeetingDto> initConductedMeetings(final String userUid) {
		return this.meetingService.findConductedMeetings(userUid, Page.TEN_RESULTS_PER_PAGE);
	}

	private List<MeetingDto> initPlannedMeetings(final String userUid) {
		return this.meetingService.findPlannedMeetings(userUid, Page.TEN_RESULTS_PER_PAGE);
	}

	public List<MeetingDto> getConductedMeetings() {
		return this.conductedMeetings;
	}

	public List<MeetingDto> getPlannedMeetings() {
		return this.plannedMeetings;
	}

	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService, "MeetingService can't be null");
		this.meetingService = meetingService;
	}
}
