package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;

import static ged.ejb.core.util.Parameters.map;
import static ged.web.core.util.Navigate.to;

import java.util.ArrayList;
import java.util.List;

import static ged.web.core.util.PageEnum.CANDIDATE;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateSearchBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Candidate> candidates;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

	private List<String> searchValues = new ArrayList<>();

	private Candidate selectedCandidate;

	public void clean() {
		this.searchText = null;
		search();
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public List<String> getSearchValues() {
		return this.searchValues;
	}

	public Candidate getSelectedCandidate() {
		return this.selectedCandidate;
	}

	@PostConstruct
	public void init() {
		search();
	}

	public void onCandidateSelect() {
		to(CANDIDATE).withParams(map("id", this.selectedCandidate.getId())).doGet();
	}

	public void search() {
		logger.debug("Searching for candidates");
		this.candidates = this.candidateService.search(this.searchText);
	}

	public void searchPlus() {
		logger.debug("Searching plus for candidates");
		this.searchValues.add(this.searchText);
		this.candidates = this.candidateService.search(this.searchText);
		this.searchText = null;
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public void setSearchValues(final List<String> searchValues) {
		this.searchValues = searchValues;
	}

	public void setSelectedCandidate(final Candidate selectedCandidate) {
		this.selectedCandidate = selectedCandidate;
	}
}