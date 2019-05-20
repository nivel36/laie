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
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class SelectCandidateBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8503929833968698420L;

	private final List<Long> alredySelected = new ArrayList<>();

	private CandidateLazyDataModel candidateLazyDataModel;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

	private List<Candidate> selectedCandidates;

	public void cancel() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void clean() {
		this.searchText = null;
		this.search();
	}

	public CandidateLazyDataModel getCandidateLazyDataModel() {
		return this.candidateLazyDataModel;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public List<Candidate> getSelectedCandidates() {
		return this.selectedCandidates;
	}

	@PostConstruct
	public void init() {
		final String jobCandidatesIdParameter = this.externalContext.getRequestParameterMap().get("jobCandiatesId");
		if (jobCandidatesIdParameter != null) {
			final String[] ids = this.externalContext.getRequestParameterMap().get("jobCandiatesId").split("\\|");
			for (final String id : ids) {
				this.alredySelected.add(Long.valueOf(id));
			}
		}
		this.candidateLazyDataModel = new CandidateLazyDataModel(this.candidateService);
	}

	public boolean isAlredySelected(final Long id) {
		return this.alredySelected.contains(id);
	}

	public void search() {
		logger.debug("Searching for candidates");
		this.candidateLazyDataModel.setSearchText(this.searchText);
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
