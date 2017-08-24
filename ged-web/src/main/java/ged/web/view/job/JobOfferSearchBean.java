package ged.web.view.job;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class JobOfferSearchBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8777365288968792501L;
	
	private String clientName;

	@Inject
	private transient JobOfferService jobOfferService;

	private String jobOfferName;

	private List<JobOffer> jobOffers;

	public void clean() {
		logger.debug("Clean job offer search fields action performed");
		cleanSearchFields();
		search();
	}

	private void cleanSearchFields() {
		this.jobOfferName = null;
		this.clientName = null;
	}

	public String getClientName() {
		return this.clientName;
	}

	public String getJobOfferName() {
		return jobOfferName;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	@PostConstruct
	public void init() {
		logger.trace("JobOfferSearchBean init");
		search();
	}

	public String newJobOffer() {
		logger.debug("New job offer action performed");
		return "jobOfferEdit?faces-redirect=true";
	}

	public void search() {
		logger.debug("Search job offer action performed");
		jobOffers = this.jobOfferService.searchByNameAndClient(this.jobOfferName, this.clientName, null);
		addWarningMessageIfMaxSearchResultsHaveBeenReached(jobOffers);
	}

	public void setClientName(final String clientName) {
		this.clientName = clientName;
	}

	public void setJobOfferName(final String jobOfferName) {
		this.jobOfferName = jobOfferName;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}