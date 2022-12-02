package es.nivel36.laie.web.view.job;

import javax.faces.view.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class SearchJobView extends AbstractView {

	private static final long serialVersionUID = -6016753467609521106L;

	private static final Logger logger = LoggerFactory.getLogger(SearchJobView.class);

	@Inject
	private JobOfferLazyDataModel jobOffers;

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

	public void search() {
		logger.debug("Search job offer action performed");
		if (this.searchText != null && this.searchText.length() > 2) {
			this.jobOffers.setSearchText(this.searchText);
		} else {
			this.searchText = null;
			this.jobOffers.setSearchText(null);
		}
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}