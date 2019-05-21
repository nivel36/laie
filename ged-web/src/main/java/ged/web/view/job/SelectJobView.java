package ged.web.view.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ged.ejb.core.model.Page;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SelectJobView extends AbstractView {

	private static final long serialVersionUID = 2489802687772948858L;

	private List<JobOffer> jobOffers;

	@Inject
	private JobOfferService jobOfferService;

	private String searchText;

	private List<JobOffer> selectedJobOffers;

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

	public List<JobOffer> getSelectedJobOffers() {
		return this.selectedJobOffers;
	}

	@PostConstruct
	public void init() {
		this.search();
	}

	public void search() {
		this.jobOffers = this.jobOfferService.search(this.searchText, Page.ALL).getResultData();
	}

	public void select() {
		PrimeFaces.current().dialog().closeDynamic(this.selectedJobOffers);
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedJobOffers(final List<JobOffer> selectedJobOffers) {
		this.selectedJobOffers = selectedJobOffers;
	}
}