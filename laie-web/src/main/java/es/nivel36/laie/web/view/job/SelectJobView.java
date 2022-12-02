package es.nivel36.laie.web.view.job;

import java.util.List;

import javax.faces.view.ViewScoped;

import org.primefaces.PrimeFaces;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class SelectJobView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	private JobOfferLazyDataModel jobOffers;

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

	public void search() {
		this.jobOffers.setSearchText(this.searchText);
	}

	public void select() {
		PrimeFaces.current().dialog().closeDynamic(this.selectedJobOffers);
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedJobOffers(final List<JobOffer> selectedJobOffers) {
		this.selectedJobOffers = selectedJobOffers;
	}
}