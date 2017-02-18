package ged.web.view.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class JobOfferViewBean extends AbstractPageBean {

	private static final long serialVersionUID = -1200840678252895578L;

	private List<Candidate> candidates = new ArrayList<>();

	private transient final CandidateService candidateService;

	private List<JobMeeting> conductedJobMeetings = new ArrayList<>();

	private boolean editable;

	private JobOffer jobOffer;

	private String jobOfferId;

	private transient final JobOfferService jobService;

	private List<JobMeeting> plannedJobMeetings = new ArrayList<>();

	@Inject
	public JobOfferViewBean(final JobOfferService jobService, final CandidateService candidateService) {
		Objects.requireNonNull(jobService);
		Objects.requireNonNull(candidateService);
		this.jobService = jobService;
		this.candidateService = candidateService;
	}

	public void addJobCandidature(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		this.jobService.addJobCandidature(this.jobOffer, candidate);
	}

	private boolean canEdit() {
		final User owner = this.jobOffer.getOwner();
		final User user = this.sessionBean.getUser();
		if (owner.equals(user)) {
			return true;
		}
		if (user.hasRole("ADMIN")) {
			return true;
		}
		if (user.hasRole("RECRUITER_ADMIN")) {
			return true;
		}
		return false;
	}

	public String edit() {
		this.flash.put("jobOffer", this.jobOffer);
		return "jobOfferEdit?faces-redirect=true";
	}

	private void error() {
		final NavigationHandler navigationHandler = this.facesContext.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(this.facesContext, null, "jobOfferSearch?faces-redirect=true");
		this.facesContext.renderResponse();
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public List<JobMeeting> getConductedJobMeetings() {
		return this.conductedJobMeetings;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public String getJobOfferId() {
		return this.jobOfferId;
	}

	public List<JobMeeting> getPlannedJobMeetings() {
		return this.plannedJobMeetings;
	}

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (this.jobOfferId != null) {
			try {
				final Long id = Long.parseLong(this.jobOfferId);
				this.jobOffer = this.jobService.find(id);
				if (this.jobOffer == null) {
					error();
				} else {
					populateJobMeetings(this.jobOffer);
					this.candidates = this.candidateService.findAllByJobOffer(this.jobOffer);
				}
				this.editable = canEdit();
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	public boolean isEditable() {
		return this.editable;
	}

	private void populateJobMeetings(final JobOffer jobOffer) {
		this.plannedJobMeetings = null;
		this.conductedJobMeetings = null;
	}

	public void removeCandidate(final Candidate candidate) {
		this.candidates.remove(candidate);
		this.jobService.removeJobCandidature(this.jobOffer, candidate);
	}

	public void setCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		if (this.candidates.contains(candidate)) {
			return;
		}
		this.candidates.add(candidate);
		this.jobService.addJobCandidature(this.jobOffer, candidate);
	}

	public void setCandidates(final List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public void setConductedJobMeetings(final List<JobMeeting> conductedJobMeetings) {
		this.conductedJobMeetings = conductedJobMeetings;
	}

	public void setJobMeeting(final JobMeeting jobMeeting) {
		Objects.requireNonNull(jobMeeting);

	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferId(final String jobOfferId) {
		this.jobOfferId = jobOfferId;
	}

	public void setPlannedJobMeetings(final List<JobMeeting> plannedJobMeetings) {
		this.plannedJobMeetings = plannedJobMeetings;
	}
}