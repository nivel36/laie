package ged.web.view;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.model.Page;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class IndexView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private List<Candidate> candidates;

	@Inject
	private transient CandidateService candidateService;
	
	private List<JobOffer> jobOffers;
	
	@Inject
	private transient JobOfferService jobService;
	
	private List<Meeting> meetings;
	
	@Inject
	private transient MeetingService meetingService;

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public LocalDate getInitialDate() {
		return LocalDate.now();
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	@PostConstruct
	public void init() {
		final User user = this.sessionUser.get();
		this.jobOffers = this.jobService.findJobOffers(user, new Page(0,10));
		this.candidates = this.candidateService.search(null, new Page(0,10)).getResultData();
		this.meetings = initMeetings();
	}

	private List<Meeting> initMeetings() {
		return meetingService.findPlannedMeetings(sessionUser.get(), Page.of(0,10));
	}
	
	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}
