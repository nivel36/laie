package ged.web.view.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class JobOfferPanelBean extends AbstractBean {

	private static final long serialVersionUID = -8972909678807698224L;

	private Long candidateId;

	private Long clientId;

	private List<JobOffer> jobOffers;

	@Inject
	private transient JobOfferService jobOfferService;

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
			findJobOffersByClient(clientIdValue);
		}
		else if (candidateIdValue != null) {
			findJobOffersByCandidate(candidateIdValue);
		}
		else {
			findJobOffersByOwner();
		}
	}

	private void findJobOffersByOwner() {
		final User owner = this.sessionBean.getUser();
		this.jobOffers = this.jobOfferService.findAllJobOffersByOwner(owner);
	}

	private void findJobOffersByCandidate(final String candidateIdValue) {
		this.candidateId = Long.parseLong(candidateIdValue);
		this.jobOffers = this.jobOfferService.findJobOffersByCandidateId(this.candidateId);
	}

	private void findJobOffersByClient(final String clientIdValue) {
		this.clientId = Long.parseLong(clientIdValue);
		this.jobOffers = this.jobOfferService.findJobOffersByClientId(this.clientId);
	}

	public boolean isNewJobOfferVisible() {
		if (this.candidateId != null) {
			return false;
		}
		return true;
	}

	public void setJobOfferService(final JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}