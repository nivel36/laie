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
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferSearchBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8777365288968792501L;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private String searchText;

	public void clean() {
		this.searchText = null;
		this.search();
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public String getSearchText() {
		return this.searchText;
	}

	@PostConstruct
	public void init() {
		logger.trace("JobOfferSearchBean init");
		this.search();
	}

	public void search() {
		logger.debug("Search job offer action performed");
		this.jobOffers = this.jobOfferService.search(this.searchText);
		this.addWarningMessageIfMaxSearchResultsHaveBeenReached(this.jobOffers);
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}