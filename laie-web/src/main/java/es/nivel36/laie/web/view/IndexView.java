package es.nivel36.laie.web.view;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.meeting.Meeting;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.action.ActionsByUserLazyDataModel;
import es.nivel36.laie.web.view.candidate.CandidateLazyDataModel;
import es.nivel36.laie.web.view.job.JobOfferLazyDataModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class IndexView extends AbstractView {

	private static final long serialVersionUID = 469723251635970418L;
	private static final Logger logger = LoggerFactory.getLogger(IndexView.class);
	public static final String URL = "/index.xhtml";

	private @Inject ActionsByUserLazyDataModel actions;
	private @Inject CandidateLazyDataModel candidates;
	private @Inject JobOfferLazyDataModel jobOffers;
	private transient @Inject MeetingService meetingService;

	private List<Meeting> meetings;

	@PostConstruct
	public void init() {
		logger.trace("Index init");
		final User user = this.sessionUser.get();
		this.meetings = this.meetingService.findFutureMeetingsByOwner(user, Page.FIRST_TEN_RESULTS);
	}

	public ActionsByUserLazyDataModel getActions() {
		return actions;
	}

	public CandidateLazyDataModel getCandidates() {
		return this.candidates;
	}

	public JobOfferLazyDataModel getJobOffers() {
		return this.jobOffers;
	}

	public List<Meeting> getMeetings() {
		return this.meetings;
	}

	public void setMeetingService(final MeetingService meetingService) {
		Objects.requireNonNull(meetingService);
		this.meetingService = meetingService;
	}
}
