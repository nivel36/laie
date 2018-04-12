package ged.web.view.candidate;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Param;

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

	@Inject
	@Param
	private Long jobOfferId;

	@Inject
	private JobOfferService jobOfferService;

	private void addCandidatesToJobOffer(final List<Candidate> selectedCandidates) {
		final JobOffer jobOffer = this.jobOfferService.find(this.jobOfferId);
		for (final Candidate candidate : selectedCandidates) {
			this.jobOfferService.addJobCandidature(jobOffer, candidate);
		}
	}

	@Override
	public void onCloseDialog(final Object value) {
		@SuppressWarnings("unchecked")
		final List<Candidate> selectedCandidates = (List<Candidate>) value;
		this.candidates.addAll(selectedCandidates);
		if (this.jobOfferId != null) {
			this.addCandidatesToJobOffer(selectedCandidates);
		}
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public Long getJobOfferId() {
		return this.jobOfferId;
	}

	@PostConstruct
	public void init() {
		if (this.jobOfferId != null) {
			this.candidates = this.candidateService.findByJobOfferId(this.jobOfferId);
		}
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setJobOfferId(final Long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}
}