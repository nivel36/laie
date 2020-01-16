package ged.web.view.job;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.map.HashedMap;
import org.omnifaces.cdi.Param;

import ged.ejb.candidate.Candidate;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.ejb.job.offer.JobOffer;
import ged.web.core.util.PageEnum;
import ged.web.core.view.AbstractView;
import ged.web.view.candidate.CandidateLazyDataModel;

@Named
@ViewScoped
public class SelectCandidatesView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private CandidateLazyDataModel candidates;

	private List<Candidate> selectedCandidates;

	@Inject
	private JobCandidatureService jobCandidatureService;

	public void setJobCandidatureService(JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

	public List<Candidate> getSelectedCandidates() {
		return selectedCandidates;
	}

	public void setSelectedCandidates(List<Candidate> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}

	private Map<String, Candidate> alredySelectedCandidates = new HashedMap<>();

	@Inject
	@Param(name = "jobOfferId", required = true)
	private JobOffer jobOffer;

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	@PostConstruct
	public void init() {
		for (final JobCandidature jobCandidature : jobOffer.getJobCandidatures()) {
			final Candidate candidate = jobCandidature.getCandidate();
			alredySelectedCandidates.put(candidate.getUid(), candidate);
		}
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	private String searchText;

	public CandidateLazyDataModel getCandidates() {
		return candidates;
	}

	public void search() {
		this.candidates.setSearchText(this.searchText);
	}

	public boolean isAlredySelected(final Candidate candidate) {
		return this.alredySelectedCandidates.containsKey(candidate.getUid());
	}

	public String select() {
		this.jobCandidatureService.addJobCandidatures(jobOffer, selectedCandidates);
		return navigator.getRedirectUrl(PageEnum.JOB, jobOffer);
	}

	public void setCandidates(CandidateLazyDataModel candidates) {
		this.candidates = candidates;
	}
}