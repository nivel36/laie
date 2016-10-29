package ged.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.job.meeting.JobMeeting;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class JobOfferViewBean extends AbstractPageBean {

	private static final long serialVersionUID = -1200840678252895578L;

	private List<JobMeeting> conductedJobMeetings = new ArrayList<>();

	private boolean editable;

	private JobOffer jobOffer;

	private String jobOfferId;

	private transient final JobOfferService jobService;

	private List<JobMeeting> plannedJobMeetings = new ArrayList<>();

	@Inject
	public JobOfferViewBean(final JobOfferService jobService) {
		if (jobService == null) {
			throw new NullPointerException();
		}
		this.jobService = jobService;
	}

	public String addCandidate() {
		return "/faces/candidate/candidateSearch?faces-redirect=true";
	}

	public void addJobCandidature(final Candidate candidate) {
		if (candidate == null) {
			throw new NullPointerException();
		}
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

	public void setConductedJobMeetings(final List<JobMeeting> conductedJobMeetings) {
		this.conductedJobMeetings = conductedJobMeetings;
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