package ged.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.JobMeeting;
import ged.ejb.job.JobOffer;
import ged.ejb.job.JobService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferViewBean extends AbstractBean {

	private static final long serialVersionUID = -1200840678252895578L;

	private List<JobMeeting> conductedJobMeetings = new ArrayList<JobMeeting>();

	private JobOffer jobOffer;

	private String jobOfferId;

	@Inject
	private JobService jobService;

	private List<JobMeeting> plannedJobMeetings = new ArrayList<JobMeeting>();

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
				this.jobOffer = this.jobService.findJobOfferById(id);
				if (this.jobOffer == null) {
					error();
				} else {
					populateJobMeetings(this.jobOffer);
				}
			} catch (final NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	private void populateJobMeetings(final JobOffer jobOffer) {
		this.plannedJobMeetings = this.jobService.findPlannedJobMeetingsByJobOffer(jobOffer);
		this.conductedJobMeetings = this.jobService.findConductedJobMeetingsByJobOffer(jobOffer);
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
