package ged.web.view.job;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.model.Page;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;

public abstract class AbstractJobView extends AbstractView {

	private static final long serialVersionUID = 1L;

	@Inject
	protected transient ClientService clientService;

	protected JobOffer jobOffer;

	@Inject
	protected transient JobOfferService jobOfferService;

	private List<User> recruiters = new ArrayList<>();

	@Inject
	protected transient UserService userService;

	public List<Client> completeClient(final String query) {
		return this.clientService.search(query, Page.of(0, 10)).getResultData();
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public List<User> getRecruiters() {
		return this.recruiters;
	}

	protected String jobUrl() {
		return PageEnum.JOB.getRedirectedUrl(this.jobOffer);
	}

	public void onClientSelect(final SelectEvent event) {
		final Object selectedObject = event.getObject();
		if (selectedObject != null) {
			final Client client = (Client) selectedObject;
			this.jobOffer.setClient(client);
		}
	}

	public void onOwnerSelect(final SelectEvent event) {
		final Object selectedObject = event.getObject();
		if (selectedObject != null) {
			final User owner = (User) selectedObject;
			this.jobOffer.setOwner(owner);
		}
	}

	public List<User> queryOwner(final String query) {
		return this.userService.search(query, Page.ALL).getResultData();
	}

	public List<User> queryRecruiter(final String query) {
		final List<User> searchRecruiter = this.userService.search(query, Page.ALL).getResultData();
		searchRecruiter.removeAll(this.recruiters);
		searchRecruiter.remove(this.jobOffer.getOwner());
		return searchRecruiter;
	}

	public void searchClient() {
		this.openBigDialog(PageEnum.CLIENT_SELECT.getUrl());
	}

	public void searchOwner() {
		this.openBigDialog(PageEnum.USER_SELECT.getUrl());
	}

	public void searchRecruiter() {
		this.openBigDialog(PageEnum.USER_SELECT.getUrl());
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public void setRecruiters(final List<User> recruiters) {
		this.recruiters = recruiters;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
