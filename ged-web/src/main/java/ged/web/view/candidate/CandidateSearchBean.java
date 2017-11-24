package ged.web.view.candidate;

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

	protected List<Candidate> candidates;

	@Inject
	protected transient CandidateService candidateService;

	private List<Candidate> lastAddedCandidates;

	private String name;

	public void clean() {
		this.name = null;
		search();
	}

	public List<Candidate> getCandidates() {
		return this.candidates;
	}

	public List<Candidate> getLastAddedCandidates() {
		return this.lastAddedCandidates;
	}

	public String getName() {
		return this.name;
	}

	@PostConstruct
	public void init() {
		search();
		this.lastAddedCandidates = this.candidateService.findLastAddedCandidates(6);
	}

	public String newCandidate() {
		logger.debug("New candidate");
		return "candidateEdit?faces-redirect=true";
	}

	public void search() {
		logger.debug("Searching for candidates");
		final List<String> searchValues;
		if (this.name != null) {
			searchValues = Arrays.asList(this.name.split("\\s"));
		} else {
			searchValues = new ArrayList<>();
		}
		this.candidates = this.candidateService.search(searchValues);
		sortCandidates(this.candidates);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setName(final String name) {
		this.name = name;
	}

	private void sortCandidates(final List<Candidate> candidates) {
		candidates.sort(Comparator.comparing(Candidate::getFullName));
	}

	public String view() {
		return "candidateEdit?faces-redirect=true&includeViewParams=true";
	}
}