package ged.web.view.candidate;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.Address;
import ged.ejb.core.model.Page;
import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.meeting.Meeting;
import ged.ejb.job.meeting.MeetingService;
import ged.ejb.job.offer.JobOffer;
import ged.web.core.IllegalPageStateException;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class ViewCandidateView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	@Inject
	@Param(name = "id", required = true)
	private Candidate candidate;

	private Curriculum curriculum;

	@Inject
	private transient CurriculumService curriculumService;

	private boolean editable;

	private List<JobCandidature> jobCandidatures;

	@Inject
	private transient JobCandidatureService jobCandidatureService;

	private List<Meeting> meetings;

	@Inject
	private transient MeetingService meetingService;

	public String editCandidate() {
		logger.debug("Edit candidate action performed");
		return navigator.getRedirectUrl(PageEnum.CANDIDATE_EDIT, candidate);
	}

	public void export() throws IOException {
		logger.debug("Export candidate action performed");
	}

	public Candidate getCandidate() {
		return this.candidate;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public List<JobCandidature> getJobCandidatures() {
		return this.jobCandidatures;
	}

	public List<Meeting> getMeetings() {
		return this.meetings;
	}

	@PostConstruct
	public void init() {
		if (this.candidate == null) {
			throw new IllegalPageStateException();
		}
		logger.trace("Candidate {} init", this.candidate);
		if (this.candidate.getAddress() == null) {
			this.candidate.setAddress(new Address());
		}
		this.jobCandidatures = this.jobCandidatureService.findJobCandidatures(this.candidate, Page.ALL_RESULTS);
		this.curriculum = this.curriculumService.findByCandidate(this.candidate);
		this.editable = this.sessionUser.hasPermissionToEdit(this.candidate);
		this.meetings = this.initMeetings();
	}

	private List<Meeting> initMeetings() {
		return this.meetingService.findMeetings(this.candidate, Page.TEN_RESULTS_PER_PAGE);
	}

	public boolean isEditable() {
		return this.editable;
	}

	public String newMeeting() {
		this.putValueToFlash("attendee", this.candidate);
		return navigator.getRedirectUrl(PageEnum.MEETING_ADD);
	}

	public void onCloseSelectJobOfferDialog(final SelectEvent event) {
		@SuppressWarnings("unchecked")
		final List<JobOffer> selectedJobOffers = (List<JobOffer>) event.getObject();
		if (selectedJobOffers == null) {
			return;
		}
		for (final JobOffer jobOffer : selectedJobOffers) {
			final JobCandidature jobCandidature = this.jobCandidatureService.addJobCandidature(jobOffer,
					this.candidate);
			this.jobCandidatures.add(jobCandidature);
		}
	}

	public void selectJobOffer() {
		logger.debug("Select job action performed");
		this.openBigDialog(PageEnum.JOB_SELECT.getUrl());
	}

	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

	public void setMeetingService(final MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}