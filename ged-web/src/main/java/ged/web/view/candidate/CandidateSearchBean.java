package ged.web.view.candidate;

import java.io.IOException;
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
import ged.web.core.view.AbstractPageBean;

@Named
@ViewScoped
public class CandidateSearchBean extends AbstractPageBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;

	private List<Candidate> candidates;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

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

	public Candidate getSelectedCandidate() {
		return this.selectedCandidate;
	}

	@PostConstruct
	public void init() {
		search();
	}

	public void onCandidateSelect() {
		try {
			final String context = this.externalContext.getContextName();
			final StringBuilder url = new StringBuilder();
			url.append("/").append(context).append("/faces/candidate/candidate.xhtml?id=")
					.append(this.selectedCandidate.getId());
			this.externalContext.redirect(url.toString());
		} catch (final IOException e) {
			logger.error("Unable to redirect to page");
		}
	}

	public void search() {
		logger.debug("Searching for candidates");
		this.candidates = this.candidateService.search(this.searchText);
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
}