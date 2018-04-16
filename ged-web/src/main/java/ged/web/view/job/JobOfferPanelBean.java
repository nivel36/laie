package ged.web.view.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.CloseDialogListener;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferPanelBean extends AbstractBean implements CloseDialogListener {

	private static final long serialVersionUID = -8972909678807698224L;

	private Long candidateId;

	@Inject
	private transient CandidateService candidateService;

	private Long clientId;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

	private void findJobOffersByCandidate(final String candidateIdValue) {
		this.candidateId = Long.parseLong(candidateIdValue);
		this.jobOffers = this.jobOfferService.findJobOffersByCandidateId(this.candidateId);
	}

	private void findJobOffersByClient(final String clientIdValue) {
		this.clientId = Long.parseLong(clientIdValue);
		this.jobOffers = this.jobOfferService.findJobOffersByClientId(this.clientId);
	}

	private void findJobOffersByOwner() {
		final User owner = this.sessionBean.getUser();
		this.jobOffers = this.jobOfferService.findAllJobOffersByOwner(owner);
	}

	public Long getCandidateId() {
		return this.candidateId;
	}

	public Long getClientId() {
		return this.clientId;
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
		return this.candidateId != null;
	}

	public boolean isNewJobOfferVisible() {
		return this.candidateId == null;
	}

	@Override
	public void onCloseDialog(final Object value) {
		@SuppressWarnings("unchecked")
		final List<JobOffer> selectedJobOffers = (List<JobOffer>) value;
		final Candidate candidate = this.candidateService.find(this.candidateId);
		for (final JobOffer jobOffer : selectedJobOffers) {
			this.jobOfferService.addJobCandidature(jobOffer, candidate);
		}
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}