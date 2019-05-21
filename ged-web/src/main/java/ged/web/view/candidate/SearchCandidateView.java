package ged.web.view.candidate;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.candidate.CandidateService;
import ged.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchCandidateView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 2434819723782902618L;
	
	private CandidateLazyDataModel candidateLazyDataModel;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

	public void export() throws IOException {
		logger.debug("Export candidates action performed");
	}

	public CandidateLazyDataModel getCandidateLazyDataModel() {
		return candidateLazyDataModel;
	}

	public String getSearchText() {
		return this.searchText;
	}

	@PostConstruct
	public void init() {
		logger.trace("Search candidate init");
		candidateLazyDataModel = new CandidateLazyDataModel(candidateService);
	}

	public void search() {
		logger.debug("Search candidates action performed");
		candidateLazyDataModel.setSearchText(searchText);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}