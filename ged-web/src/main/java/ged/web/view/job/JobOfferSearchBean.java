package ged.web.view.job;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.JobOffer;
import ged.ejb.job.JobService;
import ged.web.core.view.AbstractBean;
import ged.web.core.view.Paginator;

@Named
@ViewScoped
public class JobOfferSearchBean extends AbstractBean {

	private static final long serialVersionUID = 8777365288968792501L;

	private String clientName;

	private Paginator<JobOffer> jobOfferPaginator;

	@Inject
	private JobService jobService;

	private String name;

	public void clean() {
		cleanSearchFields();
		search();
	}

	private void cleanSearchFields() {
		this.name = null;
		this.clientName = null;
	}

	public String edit(final JobOffer jobOffer) {
		this.flash.put("jobOffer", jobOffer);
		return "jobOfferEdit?faces-redirect=true";
	}

	public String getClientName() {
		return this.clientName;
	}

	public Paginator<JobOffer> getJobOfferPaginator() {
		return this.jobOfferPaginator;
	}

	public String getName() {
		return this.name;
	}

	@PostConstruct
	public void init() {
		this.jobOfferPaginator = new Paginator<JobOffer>(this.sessionBean.getRowsPerPage());
		search();
	}

	public String newJobOffer() {
		return "jobOfferEdit?faces-redirect=true";
	}

	public void remove(final JobOffer jobOffer) {
		this.jobService.deleteJobOffer(jobOffer);
		search();
	}

	public void search() {
		this.jobOfferPaginator.setEntities(this.jobService.findAllJobOffers());
		cleanSearchFields();
	}

	public void setClientName(final String clientName) {
		this.clientName = clientName;
	}

	public void setJobOfferPaginator(final Paginator<JobOffer> jobOfferPaginator) {
		this.jobOfferPaginator = jobOfferPaginator;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
