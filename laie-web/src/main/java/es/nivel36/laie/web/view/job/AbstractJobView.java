package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractJobView extends AbstractView {

	private static final long serialVersionUID = 3680467978756165892L;

	@Param
	protected JobOffer jobOffer;

	protected transient List<User> recruiters = new ArrayList<>();

	@Inject
	protected transient ClientService clientService;

	@Inject
	protected transient JobOfferService jobOfferService;

	@Inject
	protected transient UserService userService;

	public List<Client> completeClient(final String query) {
		return this.clientService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
	}

	public List<User> queryOwner(final String query) {
		return this.userService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
	}

	public List<User> queryRecruiter(final String query) {
		return this.userService.search(query, Page.ALL_RESULTS).getResultData();
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public List<User> getRecruiters() {
		return this.recruiters;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setRecruiters(final List<User> recruiters) {
		this.recruiters = recruiters;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}
