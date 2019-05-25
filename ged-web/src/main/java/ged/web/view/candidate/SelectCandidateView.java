package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SelectCandidateView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8503929833968698420L;

	private List<Long> alredySelected;

	private CandidateLazyDataModel candidates;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

	private List<Candidate> selectedCandidates;

	public void cancel() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public CandidateLazyDataModel getCandidates() {
		return this.candidates;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public List<Candidate> getSelectedCandidates() {
		return this.selectedCandidates;
	}

	@PostConstruct
	public void init() {
		logger.trace("Select candidate init");
		this.candidates = this.initCandidates();
		this.alredySelected = this.initAlredySelectedCandidates();
	}

	private List<Long> initAlredySelectedCandidates() {
		final List<Long> candidateIds = new ArrayList<>();
		final String jobCandidatesIdParameter = this.externalContext.getRequestParameterMap().get("jobCandiatesId");
		if (jobCandidatesIdParameter != null) {
			final String[] ids = jobCandidatesIdParameter.split("\\|");
			for (final String id : ids) {
				candidateIds.add(Long.valueOf(id));
			}
		}
		return candidateIds;
	}

	private CandidateLazyDataModel initCandidates() {
		return new CandidateLazyDataModel(this.candidateService);
	}

	public boolean isAlredySelected(final Long id) {
		return this.alredySelected.contains(id);
	}

	public void search() {
		logger.debug("Searching for candidates");
		this.candidates.setSearchText(this.searchText);
	}

	public void select() {
		PrimeFaces.current().dialog().closeDynamic(this.selectedCandidates);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedCandidates(final List<Candidate> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
}
