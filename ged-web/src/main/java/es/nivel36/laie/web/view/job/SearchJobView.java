package es.nivel36.laie.web.view.job;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchJobView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private JobOfferLazyDataModel jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private String searchText;

	public void export() {
		logger.debug("Export jobs action performed");
	}

	public JobOfferLazyDataModel getJobOffers() {
		return this.jobOffers;
	}

	public String getSearchText() {
		return this.searchText;
	}

	@PostConstruct
	public void init() {
		logger.trace("JobOffer search init");
		this.jobOffers = new JobOfferLazyDataModel(this.jobOfferService);
	}

	public void search() {
		logger.debug("Search job offer action performed");
		this.jobOffers.setSearchText(this.searchText);
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}