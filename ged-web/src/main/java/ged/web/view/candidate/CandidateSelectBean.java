package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.core.ActionCallback;
import ged.web.core.view.AbstractBean;

@Named
@ViewScoped
public class CandidateSelectBean extends AbstractBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8503929833968698420L;

	private ActionCallback<List<Candidate>> action;

	private List<Candidate> candidates;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

	private List<String> searchValues = new ArrayList<>();

	private List<Candidate> selectedCandidates;

	private String updateElement;

	public void action(final ActionCallback<List<Candidate>> action, final String updateElement) {
		this.action = action;
		this.updateElement = updateElement;
	}

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

	public List<Candidate> getSelectedCandidates() {
		return this.selectedCandidates;
	}

	public String getUpdateElement() {
		return this.updateElement;
	}

	@PostConstruct
	public void init() {
		search();
	}

	public void search() {
		logger.debug("Searching for candidates");
		this.candidates = this.candidateService.search(this.searchText);
	}

	public void select() {
		this.action.doAction(this.selectedCandidates);
		this.selectedCandidates.clear();
		Ajax.update("candidateSelectForm", this.updateElement);
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

	public void setSelectedCandidates(final List<Candidate> selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
}
