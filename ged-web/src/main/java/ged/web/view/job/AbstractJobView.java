package ged.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import ged.ejb.client.Client;
import ged.ejb.core.model.Page;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

public abstract class AbstractJobView extends AbstractView {

	private static final long serialVersionUID = 747123354355904789L;

	protected JobOffer jobOffer;

	@Inject
	protected transient JobOfferService jobOfferService;

	private List<String> recruiters = new ArrayList<>();

	@Inject
	protected transient UserService userService;

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public List<String> getRecruiters() {
		return this.recruiters;
	}

	protected String jobUrl() {
		return PageEnum.JOB.getRedirectUrl(this.jobOffer);
	}

	public void onClientSelect(final SelectEvent event) {
		final Object selectedObject = event.getObject();
		if (selectedObject != null) {
			final Client client = (Client) selectedObject;
			this.jobOffer.setClient(client);
		}
	}

	public void searchClient() {
		this.openBigDialog(PageEnum.CLIENT_SELECT.getUrl());
	}

	public List<User> searchOwner(final String query) {
		if ((query == null) || (query.trim().length() < 3)) {
			return new ArrayList<>();
		}
		return this.userService.search(query, Page.ALL).getResultData();
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setRecruiters(final List<String> recruiters) {
		this.recruiters = recruiters;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
