package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;
import jakarta.inject.Inject;

public abstract class AbstractJobView extends AbstractView {

	private static final long serialVersionUID = 3680467978756165892L;

	protected @Param JobOffer jobOffer;

	protected transient List<User> recruiters = new ArrayList<>();

	protected transient @Inject ClientService clientService;

	protected transient @Inject JobOfferService jobOfferService;

	protected transient @Inject UserService userService;

	public List<Client> completeClient(final String query) {
		return this.clientService.search(query, Page.FIRST_TEN_RESULTS).hits();
	}

	public List<User> queryOwner(final String query) {
		return this.userService.searchUsers(query, Page.FIRST_TEN_RESULTS).hits();
	}

	public List<User> queryRecruiter(final String query) {
		return this.userService.searchUsers(query, Page.FIRST_TEN_RESULTS).hits();
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public void addClient() {
		this.jobOffer.setAddress(this.jobOffer.getClient().getAddress());
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
