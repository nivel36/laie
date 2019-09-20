package ged.web.view;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.CandidateService;
import ged.ejb.core.model.Page;
import ged.ejb.event.EventService;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractView;
import ged.web.view.candidate.CandidateLazyDataModel;
import ged.web.view.event.EventLazyDataModel;

@Named
@ViewScoped
public class IndexView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private CandidateLazyDataModel candidates;

	@Inject
	private transient CandidateService candidateService;

	private EventLazyDataModel events;

	@Inject
	private transient EventService eventService;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobService;

	private List<Meeting> meetings;

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

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public List<Meeting> getMeetings() {
		return this.meetings;
	}

	@PostConstruct
	public void init() {
		final User user = this.sessionUser.get();
		this.jobOffers = this.jobService.findJobOffers(user, new Page(0, 10));
		this.candidates = new CandidateLazyDataModel(this.candidateService);
		this.candidates.setSearchText(null);
		this.meetings = this.initMeetings();
		this.events = new EventLazyDataModel(this.eventService);
		this.events.setSearchText(null);
	}

	private List<Meeting> initMeetings() {
		return this.meetingService.findPlannedMeetings(this.sessionUser.get(), Page.of(0, 10));
	}

	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
