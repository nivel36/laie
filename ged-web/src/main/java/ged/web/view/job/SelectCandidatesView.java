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
import ged.ejb.candidate.CandidateService;
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

	private Map<String, Candidate> alredySelectedCandidates = new HashedMap<>();

	private CandidateLazyDataModel candidates;
	
	@Inject
	protected transient CandidateService candidateService;

	@Inject
	private JobCandidatureService jobCandidatureService;

	@Inject
	@Param(name = "jobOfferId", required = true)
	private JobOffer jobOffer;

	private String searchText;

	private List<Candidate> selectedCandidates;

	public CandidateLazyDataModel getCandidates() {
		return candidates;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public String getSearchText() {
		return searchText;
	}

	public List<Candidate> getSelectedCandidates() {
		return selectedCandidates;
	}

	@PostConstruct
	public void init() {
		for (final JobCandidature jobCandidature : jobOffer.getJobCandidatures()) {
			final Candidate candidate = jobCandidature.getCandidate();
			alredySelectedCandidates.put(candidate.getUid(), candidate);
		}
		this.candidates = new CandidateLazyDataModel(this.candidateService);
		search();
	}

	public boolean isAlredySelected(final Candidate candidate) {
		return this.alredySelectedCandidates.containsKey(candidate.getUid());
	}

	public void search() {
		this.candidates.setSearchText(this.searchText);
	}

	public String select() {
		this.jobCandidatureService.addJobCandidatures(jobOffer, selectedCandidates);
		return navigator.getRedirectUrl(PageEnum.JOB, jobOffer);
	}

	public void setCandidates(CandidateLazyDataModel candidates) {
		this.candidates = candidates;
	}

	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setJobCandidatureService(JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedCandidates(List<Candidate> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
}