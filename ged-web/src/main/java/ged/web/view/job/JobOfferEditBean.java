package ged.web.view.job;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.job.JobOffer;
import ged.ejb.service.job.JobService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferEditBean extends AbstractBean {

	private static final long serialVersionUID = 7362448981391968171L;

	private JobOffer jobOffer;
	
	@Inject
	private JobService jobService;

	// ////////////////////////////////////////////////////////////////////////
	// INIT
	// ////////////////////////////////////////////////////////////////////////

	@PostConstruct
	public void init() {
		if (flash.containsKey("jobOffer")) {
			jobOffer = (JobOffer) flash.get("jobOffer");
		} else {
			jobOffer = new JobOffer();
		}
	}

	// ////////////////////////////////////////////////////////////////////////
	// SET AND GETS
	// ////////////////////////////////////////////////////////////////////////

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	// ////////////////////////////////////////////////////////////////////////
	// ACTIONS
	// ////////////////////////////////////////////////////////////////////////

	public String save() {
		jobService.insertOrUpdate(jobOffer);
		return "jobOfferSearch?faces-redirect=true";
	}

	public String cancel() {
		return "jobOfferSearch?faces-redirect=true";
	}
}
