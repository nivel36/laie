package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.client.ClientService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.SimpleUser;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.view.AbstractView;

public abstract class AbstractJobView extends AbstractView {

	private static final long serialVersionUID = 3680467978756165892L;

	protected JobOffer jobOffer;

	protected transient List<SimpleUser> recruiters = new ArrayList<>();

	@Inject
	protected transient ClientService clientService;

	@Inject
	protected transient JobOfferService jobOfferService;

	@Inject
	protected transient UserService userService;

	public List<Client> completeClient(final String query) {
		return this.clientService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
	}

	public void onOwnerSelect(final SelectEvent<SimpleUser> event) {
		final SimpleUser user = event.getObject();
		if (user != null) {
			this.jobOfferService.changeJobOffersOwner(this.jobOffer.getId(), user.getId());
		}
	}

	public List<SimpleUser> queryOwner(final String query) {
		final List<User> resultData = this.userService.search(query, Page.TEN_RESULTS_PER_PAGE).getResultData();
		final List<SimpleUser> users = resultData.stream().map(SimpleUser::new).collect(Collectors.toList());
		return users;
	}

	public List<SimpleUser> queryRecruiter(final String query) {
		final List<SimpleUser> recruiters = new ArrayList<SimpleUser>();
		final List<User> searchRecruiters = this.userService.search(query, Page.ALL_RESULTS).getResultData();
		for(final User searchRecruiter:searchRecruiters) {
			final SimpleUser recruiter = new SimpleUser(searchRecruiter);
			this.recruiters.add(recruiter);
		}
		return recruiters;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public List<SimpleUser> getRecruiters() {
		return this.recruiters;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setRecruiters(final List<SimpleUser> recruiters) {
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
