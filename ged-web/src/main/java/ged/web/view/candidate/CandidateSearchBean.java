package ged.web.view.candidate;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class CandidateSearchBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Candidate> candidates;

	@Inject
	protected transient CandidateService candidateService;

	private List<Candidate> lastAddedCandidates;

	private long numberOfCandidates;

	private String searchText;

	private Candidate selectedCandidate;

	public void clean() {
		this.searchText = null;
		search();
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public List<Candidate> getLastAddedCandidates() {
		return this.lastAddedCandidates;
	}

	public long getNumberOfCandidates() {
		return this.numberOfCandidates;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public Candidate getSelectedCandidate() {
		return this.selectedCandidate;
	}

	@PostConstruct
	public void init() {
		search();
		this.numberOfCandidates = this.candidateService.findNumberOfCandidates();
		this.lastAddedCandidates = this.candidateService.findLastAddedCandidates(6);
	}

	public String newCandidate() {
		logger.debug("New candidate");
		return "candidateEdit?faces-redirect=true";
	}

	public void onCandidateSelect() throws IOException {
		this.externalContext.redirect("candidateView.xhtml?id=" + this.selectedCandidate.getId());
	}

	public void search() {
		logger.debug("Searching for candidates");
		final List<String> searchValues;
		if (this.searchText != null) {
			searchValues = Arrays.asList(this.searchText.split("\\s"));
		} else {
			searchValues = new ArrayList<>();
		}
		this.candidates = this.candidateService.search(searchValues);
		sortCandidates(this.candidates);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSelectedCandidate(final Candidate selectedCandidate) {
		this.selectedCandidate = selectedCandidate;
	}

	private void sortCandidates(final List<Candidate> candidates) {
		candidates.sort(Comparator.comparing(Candidate::getFullName));
	}

	public String view() {
		return "candidateEdit?faces-redirect=true&includeViewParams=true";
	}
}