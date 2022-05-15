package es.nivel36.laie.web.view;

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
import es.nivel36.laie.web.core.view.UserActionsLazyDataModel;
import es.nivel36.laie.web.view.candidate.CandidateLazyDataModel;
import es.nivel36.laie.web.view.event.EventLazyDataModel;
import es.nivel36.laie.web.view.job.JobOfferLazyDataModel;

@Named
@ViewScoped
public class IndexView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(IndexView.class);

	private static final long serialVersionUID = 469723251635970418L;

	public static final String URL = "/index.xhtml";

	@Inject
	private UserActionsLazyDataModel actions;
	
	@Inject
	private CandidateLazyDataModel candidates;
	
	@Inject
	private EventLazyDataModel events;

	@Inject
	private JobOfferLazyDataModel jobOffers;

	private List<Meeting> meetings;

	@Inject
	private transient MeetingService meetingService;

	public UserActionsLazyDataModel getActions() {
		return actions;
	}
	
	public CandidateLazyDataModel getCandidates() {
		return this.candidates;
	}

	public EventLazyDataModel getEvents() {
		return this.events;
	}

	public JobOfferLazyDataModel getJobOffers() {
		return this.jobOffers;
	}

	public List<Meeting> getMeetings() {
		return this.meetings;
	}

	@PostConstruct
	public void init() {
		logger.trace("Index init");
		final User user = this.sessionUser.get();
		this.meetings = this.meetingService.findPlannedMeetings(user, Page.FIRST_TEN_RESULTS);
		this.events = null;
	}


	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService);
		this.meetingService = meetingService;
	}
}
