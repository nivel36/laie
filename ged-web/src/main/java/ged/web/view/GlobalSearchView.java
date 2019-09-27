package ged.web.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.core.model.Page;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class GlobalSearchView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private List<Candidate> candidates;

	@Inject
	private transient CandidateService candidateService;

	private List<Client> clients;

	@Inject
	private transient ClientService clientService;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobService;

	@Inject
	@Param(name = "searchText", required = true)
	private String searchText;

	private List<User> users;

	@Inject
	private transient UserService userService;

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public List<Client> getClients() {
		return this.clients;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	public List<User> getUsers() {
		return this.users;
	}

	@PostConstruct
	public void init() {
		this.search();
	}

	public void search() {
		if ((this.searchText == null) || (this.searchText.length() < 3)) {
			Message.addWarning("error.search.camp_to_short", "error.search.camp_to_short");
		} else {
			this.users = this.userService.search(this.searchText, Page.ALL).getResultData();
			this.jobOffers = this.jobService.search(this.searchText, Page.ALL).getResultData();
			this.candidates = this.candidateService.search(this.searchText, Page.ALL).getResultData();
			this.clients = this.clientService.search(this.searchText, Page.ALL).getResultData();
		}
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setClientService(final ClientService clientService) {
		this.clientService = clientService;
	}

	public void setJobService(final JobOfferService jobService) {
		this.jobService = jobService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}