package ged.web.view.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SelectJobView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private JobOfferLazyDataModel jobOffers;

	@Inject
	private JobOfferService jobOfferService;

	private String searchText;

	private List<JobOffer> selectedJobOffers;

	public void clean() {
		this.searchText = null;
		this.search();
	}

	public JobOfferLazyDataModel getJobOffers() {
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
		this.jobOffers = new JobOfferLazyDataModel(this.jobOfferService);
	}

	public void search() {
		this.jobOffers.setSearchText(this.searchText);
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