package ged.web.view.candidate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateService;
import ged.web.core.view.AbstractDialogBean;

@Named
@ViewScoped
public class CandidateSelectDialogBean extends AbstractDialogBean {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8503929833968698420L;

	private List<Candidate> candidates;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

	private List<String> searchValues = new ArrayList<>();

	private List<Candidate> selectedCandidates;

	public void clean() {
		this.searchText = null;
		this.search();
	}

	@Override
	protected void dispose() {
		this.searchText = null;
		this.selectedCandidates = null;
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

	@Override
	public void init() {
		this.search();
	}

	public void search() {
		logger.debug("Searching for candidates");
		this.candidates = this.candidateService.search(this.searchText);
	}

	public void select() {
		this.callback.onCloseDialog(this.selectedCandidates);
		this.selectedCandidates.clear();
		Ajax.update("candidateSelectForm", this.updateField);
		this.closeDialog();
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
