package es.nivel36.laie.web.view.candidate;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchCandidateView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private CandidateLazyDataModel candidates;

	@Inject
	protected transient CandidateService candidateService;

	private String searchText;

	public void export() {
		logger.debug("Export candidates action performed");
	}

	public CandidateLazyDataModel getCandidates() {
		return this.candidates;
	}

	public String getSearchText() {
		return this.searchText;
	}

	@PostConstruct
	public void init() {
		logger.trace("Search candidate init");
		this.candidates = this.initCandidates();
	}

	private CandidateLazyDataModel initCandidates() {
		return new CandidateLazyDataModel(this.candidateService);
	}

	public void search() {
		logger.debug("Search candidates action performed");
		this.candidates.setSearchText(this.searchText);
	}

	public void setCandidateService(final CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}