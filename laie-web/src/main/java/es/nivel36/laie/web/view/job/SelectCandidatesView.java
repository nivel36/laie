package es.nivel36.laie.web.view.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.omnifaces.cdi.Param;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.IllegalPageStateException;
import es.nivel36.laie.web.core.view.AbstractView;
import es.nivel36.laie.web.view.candidate.CandidateLazyDataModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class SelectCandidatesView extends AbstractView {

	private static final long serialVersionUID = -4200957281776169451L;

	private static final Logger logger = LoggerFactory.getLogger(SelectCandidatesView.class);

	private final Map<Long, Candidate> alredySelectedCandidates = new HashMap<>();

	private @Inject CandidateLazyDataModel candidates;

	private @Inject JobOfferService jobOfferService;

	private @Param(name = "jobOffer", required = true) String jobOfferId;

	private JobOffer jobOffer;

	private String searchText;

	private List<Candidate> selectedCandidates;

	private transient @Inject JobCandidatureService jobCandidatureService;

	@PostConstruct
	public void init() {
		logger.trace("Select Candidates for jobOffer {} init", this.jobOfferId);
		findJobOfferCandidatures();
		final User user = sessionUser.get();
		if (!(this.sessionUser.isAdmin() || this.jobOffer.getOwner().equals(user)
				|| this.jobOffer.getRecruiters().contains(user))) {
			throw new SecurityException();
		}
		final List<JobCandidature> jobCandidatures = this.jobCandidatureService.findJobCandidaturesByJobOffer(jobOffer,
				Page.ALL_RESULTS);
		for (final JobCandidature jobCandidature : jobCandidatures) {
			final Candidate candidate = jobCandidature.getCandidate();
			this.alredySelectedCandidates.put(candidate.getId(), candidate);
		}
		this.search();
	}

	private void findJobOfferCandidatures() {
		try {
			final Long id = Long.parseLong(jobOfferId);
			this.jobOffer = this.jobOfferService.findJobOfferById(id);
			if (this.jobOffer == null) {
				throw new IllegalPageStateException();
			}
		} catch (final NumberFormatException ex) {
			throw new IllegalPageStateException();
		}
	}

	public void search() {
		this.candidates.setSearchText(this.searchText);
	}

	public boolean isAlredySelected(final Candidate candidate) {
		return this.alredySelectedCandidates.containsKey(candidate.getId());
	}

	public void select() {
		this.jobCandidatureService.addJobCandidatures(jobOffer, this.selectedCandidates);
		Faces.redirect(ViewJobView.getUrl(this.jobOffer.getId()));
	}

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

	public void setCandidates(final CandidateLazyDataModel candidates) {
		this.candidates = candidates;
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

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}
}