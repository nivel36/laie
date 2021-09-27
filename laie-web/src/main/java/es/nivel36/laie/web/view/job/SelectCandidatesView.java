package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.map.HashedMap;
import org.omnifaces.cdi.Param;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.candidate.CandidateLazyDataModel;

@Named
@ViewScoped
public class SelectCandidatesView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private final Map<String, Candidate> alredySelectedCandidates = new HashedMap<>();

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
		return this.candidates;
	}

	public JobOffer getJobOffer() {
		return this.jobOffer;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public List<Candidate> getSelectedCandidates() {
		return this.selectedCandidates;
	}

	@PostConstruct
	public void init() {
		for (final JobCandidature jobCandidature : this.jobOffer.getJobCandidatures()) {
			final Candidate candidate = jobCandidature.getCandidate();
			this.alredySelectedCandidates.put(candidate.getUid(), candidate);
		}
		this.candidates = new CandidateLazyDataModel(this.candidateService);
		this.search();
	}

	public boolean isAlredySelected(final Candidate candidate) {
		return this.alredySelectedCandidates.containsKey(candidate.getUid());
	}

	public void search() {
		this.candidates.setSearchText(this.searchText);
	}

	public String select() {
		this.jobCandidatureService.addJobCandidatures(this.jobOffer, this.selectedCandidates);
		return this.navigator.getRedirectUrl(PageEnum.JOB, this.jobOffer);
	}

	public void setCandidates(final CandidateLazyDataModel candidates) {
		this.candidates = candidates;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedCandidates(final List<Candidate> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
}