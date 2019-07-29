package ged.web.view.candidate;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
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
import ged.ejb.core.tag.Tag;
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

	private static final long serialVersionUID = 1577879781927493283L;

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

	private final List<Tag> tags = new ArrayList<>();
	
	public String editCandidate() {
		logger.debug("Edit candidate action performed");
		this.putValueToFlash("candidate", this.candidate);
		return PageEnum.CANDIDATE_EDIT.getUrl();
	}

	public void export() throws IOException {
		logger.debug("Export candidate action performed");
	}
	
	private void fillTags() {
		tags.addAll(candidate.getTags());
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
		return meetings;
	}

	public List<Tag> getTags() {
		return this.tags;
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
		this.fillTags();
		this.jobCandidatures = this.jobCandidatureService.findJobCandidatures(candidate, Page.ALL);
		this.curriculum = this.curriculumService.findByCandidate(this.candidate);
		this.editable = this.sessionUser.hasPermissionToEdit(this.candidate);
		this.meetings = initMeetings();
	}

	private List<Meeting> initMeetings() {
		return meetingService.findMeetings(candidate, Page.of(0, 10));
	}

	public boolean isEditable() {
		return this.editable;
	}

	public String newMeeting() {
		this.putValueToFlash("attendee", this.candidate);
		return PageEnum.MEETING_ADD.getRedirectedUrl();
	}

	public void onCloseSelectJobOfferDialog(final SelectEvent event) {
		@SuppressWarnings("unchecked")
		final List<JobOffer> selectedJobOffers = (List<JobOffer>) event.getObject();
		if (selectedJobOffers == null) {
			return;
		}
		for (final JobOffer jobOffer : selectedJobOffers) {
			final JobCandidature jobCandidature = this.jobCandidatureService.addJobCandidature(jobOffer, this.candidate);
			jobCandidatures.add(jobCandidature);
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

	public void setJobCandidatureService(JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

}