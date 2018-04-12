package ged.web.view.job;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class JobOfferSearchDialogBean extends AbstractDialogBean {

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

	@Override
	protected void dispose() {
		this.selectedJobOffers = null;
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

	@Override
	protected void init() {
		this.jobOfferService.search(this.searchText);
	}

	public void search() {
		this.jobOffers = this.jobOfferService.search(this.searchText);
	}

	public void select() {
		this.callback.onCloseDialog(this.selectedJobOffers);
		this.selectedJobOffers.clear();
		Ajax.update("jobOfferSelectForm", this.updateField);
		this.closeDialog();
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