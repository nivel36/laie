package ged.web.view.job;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.service.client.Client;
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

	public String cancel() {
		return "jobOfferSearch?faces-redirect=true";
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	@PostConstruct
	public void init() {
		if (this.flash.containsKey("jobOffer")) {
			this.jobOffer = (JobOffer) this.flash.get("jobOffer");
		} else {
			this.jobOffer = new JobOffer();
			final Client client = new Client();
			this.jobOffer.setClient(client);
		}
	}

	public String save() {
		this.jobService.insert(this.jobOffer);
		return "jobOfferSearch?faces-redirect=true";
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
