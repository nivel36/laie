package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.model.Page;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class CandidateSelectDialogBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8503929833968698420L;

	private List<Candidate> candidates;

	@Inject
	protected transient CandidateService candidateService;

	private final List<Long> candidateToRemoveIds = new ArrayList<>();

	private String searchText;

	private List<Candidate> selectedCandidates;

	public void clean() {
		this.searchText = null;
		this.search();
	}

	public List<Candidate> getCandidates() {
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
		final String jobCandidatesIdParameter = externalContext.getRequestParameterMap().get("jobCandiatesId");
		if ((jobCandidatesIdParameter != null)) {
			final String[] ids = externalContext.getRequestParameterMap().get("jobCandiatesId").split("\\|");
			for (final String id : ids) {
				candidateToRemoveIds.add(Long.valueOf(id));
			}
		}
		search();
	}

	public void search() {
		logger.debug("Searching for candidates");
		this.candidates = this.candidateService.search(this.searchText, Page.ALL).stream().filter(e -> !candidateToRemoveIds.contains(e.getId()))
				.collect(Collectors.toList());
	}

	public void select() {
		this.closeDialog(this.selectedCandidates);
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
