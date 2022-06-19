package es.nivel36.laie.web.view.job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

	protected @Param JobOffer jobOffer;

	protected transient List<String> recruiters = new ArrayList<>();

	protected transient @Inject ClientService clientService;

	protected transient @Inject JobOfferService jobOfferService;

	protected transient @Inject UserService userService;

	public List<Client> completeClient(final String query) {
		return this.clientService.search(query, Page.FIRST_TEN_RESULTS).getResultData();
	}

	public List<User> queryOwner(final String query) {
		return this.userService.search(query, Page.FIRST_TEN_RESULTS).getResultData();
	}

	public List<User> queryRecruiter(final String query) {
		return this.userService.search(query, Page.FIRST_TEN_RESULTS).getResultData();
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}
	
	public void addClient() {		
		this.jobOffer.setAddress(this.jobOffer.getClient().getAddress());		
	}

	protected void convertRecruiters() {
		final Set<User> jobOfferRecruiters = this.jobOffer.getRecruiters();
		final Set<User> newJobOfferRecruiters = new HashSet<User>();
		for (final String email : this.recruiters) {
			boolean oldRecruiter = false;
			for (final User user : jobOfferRecruiters) {
				if (user.getEmail().equals(email)) {
					oldRecruiter = true;
					newJobOfferRecruiters.add(user);
					break;
				}
			}
			if (!oldRecruiter) {
				final User newRecruiter = this.userService.findUserByEmail(email);
				newJobOfferRecruiters.add(newRecruiter);
			}
		}
		this.jobOffer.setRecruiters(newJobOfferRecruiters);
	}

	public List<String> getRecruiters() {
		return this.recruiters;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setRecruiters(final List<String> recruiters) {
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
