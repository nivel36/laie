package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.role.Role;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class JobOfferViewBean extends AbstractPageBean {

	private static final long serialVersionUID = -1200840678252895578L;
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private List<Candidate> candidates = new ArrayList<>();

	@Inject
	private transient  CandidateService candidateService;

	private List<JobMeeting> conductedJobMeetings = new ArrayList<>();

	private boolean editable;

	private JobOffer jobOffer;

	private String jobOfferId;

	@Inject
	private transient  JobOfferService jobService;

	private List<JobMeeting> plannedJobMeetings = new ArrayList<>();

	public void addJobCandidature(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		this.jobService.addJobCandidature(this.jobOffer, candidate);
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
				final long id = Long.parseLong(this.jobOfferId);
				this.jobOffer = this.jobService.find(id);
				if (this.jobOffer == null) {
					error();
				} else {
					populateJobMeetings(this.jobOffer);
					this.candidates = this.candidateService.findAllByJobOffer(this.jobOffer);
				}
				this.editable = userHasPermissionToEditJobOffer();
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

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
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

	public void setJobService(JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setPlannedJobMeetings(final List<JobMeeting> plannedJobMeetings) {
		this.plannedJobMeetings = plannedJobMeetings;
	}

	public boolean userHasPermissionToEditJobOffer() {
		final User jobOfferOwner = jobOffer.getOwner();
		final User user = this.sessionBean.getUser();
		if (jobOfferOwner.equals(user)) {
			return true;
		}
		if (user.hasRole(Role.ADMIN) || user.hasRole(Role.RECRUITER_ADMIN)) {
			return true;
		}
		return false;
	}
}