package es.nivel36.laie.web.view;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.event.JobCandidatureEventService;
import es.nivel36.laie.ejb.job.meeting.MeetingDto;
import es.nivel36.laie.ejb.job.meeting.MeetingService;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.candidate.CandidateLazyDataModel;
import es.nivel36.laie.web.view.event.EventLazyDataModel;

@Named
@ViewScoped
public class IndexView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private CandidateLazyDataModel candidates;

	@Inject
	private transient CandidateService candidateService;

	private EventLazyDataModel events;

	@Inject
	private transient JobCandidatureEventService jobCandidatureEventService;

	private List<JobOfferDto> jobOffers;

	@Inject
	private transient JobOfferService jobService;

	private List<MeetingDto> meetings;

	@Inject
	private transient MeetingService meetingService;

	public CandidateLazyDataModel getCandidates() {
		return this.candidates;
	}

	public EventLazyDataModel getEvents() {
		return this.events;
	}

	public LocalDate getInitialDate() {
		return LocalDate.now();
	}

	public List<JobOfferDto> getJobOffers() {
		return this.jobOffers;
	}

	public List<MeetingDto> getMeetings() {
		return this.meetings;
	}

	@PostConstruct
	public void init() {
		final UserDto user = this.sessionUser.get();
		this.jobOffers = this.jobService.findJobOffersByOwner(user.getUid(), new Page(0, 10));
		this.candidates = new CandidateLazyDataModel(this.candidateService);
		this.meetings = this.initMeetings();
		this.events = new EventLazyDataModel(this.jobCandidatureEventService);
	}

	private List<MeetingDto> initMeetings() {
		return this.meetingService.findPlannedMeetings(this.sessionUser.get().getUid(), Page.TEN_RESULTS_PER_PAGE);
	}

	public void setEventService(final JobCandidatureEventService jobCandidatureEventService) {
		this.jobCandidatureEventService = jobCandidatureEventService;
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
