package ged.web.view.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.client.Client;
import ged.ejb.client.ClientService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.CloseDialogListener;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferPanelBean extends AbstractBean implements CloseDialogListener {

	private static final long serialVersionUID = -8972909678807698224L;

	private Candidate candidate;

	@Inject
	private transient CandidateService candidateService;

	private Client client;

	@Inject
	private transient ClientService clientService;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private void findJobOffersByCandidate(final String candidateIdValue) {
		final Long candidateId = Long.parseLong(candidateIdValue);
		this.candidate = this.candidateService.find(candidateId);
		this.jobOffers = this.jobOfferService.findJobOffersByCandidate(this.candidate);
	}

	private void findJobOffersByClient(final String clientIdValue) {
		final Long clientId = Long.parseLong(clientIdValue);
		this.client = this.clientService.find(clientId);
		this.jobOffers = this.jobOfferService.findJobOffersByClient(this.client);
	}

	private void findJobOffersByOwner() {
		final User owner = this.sessionBean.getUser();
		this.jobOffers = this.jobOfferService.findAllJobOffersByOwner(owner);
	}

	public Client getClient() {
		return this.client;
	}

	public List<JobOffer> getJobOffers() {
		return this.jobOffers;
	}

	@PostConstruct
	public void init() {
		final String clientIdValue = this.getValueFromGetParameters("clientId");
		final String candidateIdValue = this.getValueFromGetParameters("candidateId");
		if (clientIdValue != null) {
			this.findJobOffersByClient(clientIdValue);
		}
		else if (candidateIdValue != null) {
			this.findJobOffersByCandidate(candidateIdValue);
		}
		else {
			this.findJobOffersByOwner();
		}
	}

	public boolean isAddJobOfferVisible() {
		return this.candidate != null;
	}

	public boolean isNewJobOfferVisible() {
		return this.candidate == null;
	}

	@Override
	public void onCloseDialog(final Object value) {
		@SuppressWarnings("unchecked")
		final List<JobOffer> selectedJobOffers = (List<JobOffer>) value;
		for (final JobOffer jobOffer : selectedJobOffers) {
			this.jobOfferService.addJobCandidature(jobOffer, this.candidate);
		}
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}