package es.nivel36.laie.web.view.candidate;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.web.core.view.AbstractView;

@Named
@ViewScoped
public class SearchCandidateView extends AbstractView {

	private static final long serialVersionUID = -5639850911652213337L;

	private static final Logger logger = LoggerFactory.getLogger(SearchCandidateView.class);

	private transient @Inject AddCandidatePermission addCandidatePermission;

	private @Inject CandidateLazyDataModel candidates;

	private boolean insertable;

	private String searchText;

	@PostConstruct
	public void init() {
		logger.debug("Search candidate init");
		this.insertable = addCandidatePermission.validate(null);
	}

	public void export() {
		logger.debug("Export candidates action performed");
	}

	public CandidateLazyDataModel getCandidates() {
		return this.candidates;
	}

	public String getSearchText() {
		return this.searchText;
	}

	public void search() {
		logger.debug("Search candidates action performed");
		if (this.searchText != null && this.searchText.length() > 2) {
			this.candidates.setSearchText(this.searchText);
		} else {
			this.searchText = null;
			this.candidates.setSearchText(null);
		}
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public boolean isInsertable() {
		return this.insertable;
	}

	public void setAddCandidatePermission(AddCandidatePermission addCandidatePermission) {
		Objects.requireNonNull(addCandidatePermission);
		this.addCandidatePermission = addCandidatePermission;
	}

}