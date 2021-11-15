package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.map.HashedMap;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateDto;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.util.PageEnum;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.candidate.CandidateLazyDataModel;

@Named
@ViewScoped
public class SelectCandidatesView extends AbstractView {

	private static final long serialVersionUID = -6058282026562660524L;

	private final Map<String, Candidate> alredySelectedCandidates = new HashedMap<>();

	private CandidateLazyDataModel candidates;

	private JobOfferDto jobOffer;

	private String searchText;

	private List<CandidateDto> selectedCandidates;
	
	@Inject
	private transient CandidateService candidateService;

	@Inject
	private transient JobCandidatureService jobCandidatureService;
	
	@Inject
	private transient JobOfferService jobOfferService;

	@PostConstruct
	public void init() {
		final String uid = this.getValueFromGetParameters("jobOffer", true);
		this.jobOffer = this.jobOfferService.findJobOfferByUid(uid);
		if( this.jobOffer == null ) {
			throw new IllegalPageStateException();
		}
		for (final JobCandidature jobCandidature : this.jobOffer.getJobCandidatures()) {
			final Candidate candidate = jobCandidature.getCandidate();
			this.alredySelectedCandidates.put(candidate.getUid(), candidate);
		}
		this.candidates = new CandidateLazyDataModel(this.candidateService);
		this.search();
	}
	
	public void search() {
		this.candidates.setSearchText(this.searchText);
	}

	public boolean isAlredySelected(final Candidate candidate) {
		return this.alredySelectedCandidates.containsKey(candidate.getUid());
	}

	public String select() {
		final String[] candidatesUids = this.selectedCandidates.stream().map(CandidateDto::getTags).toArray(String[]::new);
		final String jobOfferUid = this.jobOffer.getUid();
		this.jobCandidatureService.addJobCandidatures(jobOfferUid, candidatesUids);
		return this.navigator.getRedirectUrl(PageEnum.JOB, jobOfferUid);
	}
	
	public CandidateLazyDataModel getCandidates() {
		return this.candidates;
	}

	public JobOfferDto getJobOffer() {
		return this.jobOffer;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public List<CandidateDto> getSelectedCandidates() {
		return this.selectedCandidates;
	}

	public void setCandidates(final CandidateLazyDataModel candidates) {
		this.candidates = candidates;
	}

	public void setJobOffer(final JobOfferDto jobOffer) {
		this.jobOffer = jobOffer;
	}
	
	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedCandidates(final List<CandidateDto> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
	
	public void setCandidateService(final CandidateService candidateService) {
		Objects.requireNonNull(candidateService);
		this.candidateService = candidateService;
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}
	
	public void setJobOfferService(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}
}