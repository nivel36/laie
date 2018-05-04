package ged.web.view.candidate;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.CloseDialogListener;
import ged.web.core.view.AbstractBean;

@ViewScoped
@Named
public class CandidatePanelBean extends AbstractBean implements CloseDialogListener {

	private static final long serialVersionUID = 3685531862855960321L;

	private List<Candidate> candidates;

	@Inject
	private CandidateService candidateService;

	private JobOffer jobOffer;

	@Inject
	private JobOfferService jobOfferService;

	private void addCandidatesToJobOffer(final List<Candidate> selectedCandidates) {
		for (final Candidate candidate : selectedCandidates) {
			this.jobOfferService.addJobCandidature(this.jobOffer, candidate);
		}
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	@PostConstruct
	public void init() {
		final String jobOfferIdValue = this.getValueFromGetParameters("jobOfferId");
		if (jobOfferIdValue != null) {
			final Long jobOfferId = Long.parseLong(jobOfferIdValue);
			this.jobOffer = this.jobOfferService.find(jobOfferId);
			this.candidates = this.candidateService.findCandidatesByJobOffer(this.jobOffer);
		}
	}

	@Override
	public void onCloseDialog(final Object value) {
		@SuppressWarnings("unchecked")
		final List<Candidate> selectedCandidates = (List<Candidate>) value;
		this.candidates.addAll(selectedCandidates);
		if (this.jobOffer != null) {
			this.addCandidatesToJobOffer(selectedCandidates);
		}
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}
}