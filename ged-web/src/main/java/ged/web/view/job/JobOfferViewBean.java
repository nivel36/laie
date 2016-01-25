package ged.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.job.JobMeeting;
import ged.ejb.service.job.JobOffer;
import ged.ejb.service.job.JobService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferViewBean extends AbstractBean {

	private static final long serialVersionUID = -1200840678252895578L;

	private String jobOfferId;

	private JobOffer jobOffer;

	private List<JobMeeting> plannedJobMeetings = new ArrayList<JobMeeting>();

	private List<JobMeeting> conductedJobMeetings = new ArrayList<JobMeeting>();

	@Inject
	private JobService jobService;

	// /////////////////////////////////////////////////////////////////////////
	// INIT
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Not using @PostConstruct because the view is a GET based form.
	 */
	public void init() {
		if (jobOfferId != null) {
			try {
				Long id = Long.parseLong(jobOfferId);
				jobOffer = jobService.searchById(id);
				if (jobOffer == null) {
					error();
				} else {
					populateJobMeetings(jobOffer);
				}
			} catch (NumberFormatException ex) {
				error();
			}
		} else {
			error();
		}
	}

	private void populateJobMeetings(JobOffer jobOffer) {
		plannedJobMeetings = jobService
				.searchPlannedJobMeetingsByJobOffer(jobOffer);
		conductedJobMeetings = jobService
				.searchConductedJobMeetingsByJobOffer(jobOffer);
	}

	private void error() {
		NavigationHandler navigationHandler = facesContext.getApplication()
				.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, null,
				"jobOfferSearch?faces-redirect=true");
		facesContext.renderResponse();
	}

	// /////////////////////////////////////////////////////////////////////////
	// SET AND GET
	// /////////////////////////////////////////////////////////////////////////

	public List<JobMeeting> getPlannedJobMeetings() {
		return plannedJobMeetings;
	}

	public void setPlannedJobMeetings(List<JobMeeting> plannedJobMeetings) {
		this.plannedJobMeetings = plannedJobMeetings;
	}

	public List<JobMeeting> getConductedJobMeetings() {
		return conductedJobMeetings;
	}

	public void setConductedJobMeetings(List<JobMeeting> conductedJobMeetings) {
		this.conductedJobMeetings = conductedJobMeetings;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public String getJobOfferId() {
		return jobOfferId;
	}

	public void setJobOfferId(String jobOfferId) {
		this.jobOfferId = jobOfferId;
	}

	// /////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// /////////////////////////////////////////////////////////////////////////

	public String modify() {
		flash.put("jobOffer", jobOffer);
		return "jobOfferEdit?faces-redirect=true";
	}
}
