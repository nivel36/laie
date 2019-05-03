package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.model.Page;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class SearchCandidateBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Candidate> candidates;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public String getSearchText() {
		return this.searchText;
	}

	@PostConstruct
	public void init() {
		logger.trace("Search candidate init");
		this.search();
	}

	public void search() {
		logger.debug("Search candidates action performed");
		this.candidates = this.candidateService.search(this.searchText, Page.ALL);
		this.addWarningMessageIfMaxSearchResultsHaveBeenReached(this.candidates);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}