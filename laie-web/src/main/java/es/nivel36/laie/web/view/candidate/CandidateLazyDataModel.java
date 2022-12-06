package es.nivel36.laie.web.view.candidate;

import javax.naming.directory.SearchResult;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.candidate.CandidateService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;
import jakarta.inject.Inject;

public class CandidateLazyDataModel extends AbstractLazyDataModel<Candidate> {

	private static final long serialVersionUID = -4871133088132391207L;

	private transient @Inject CandidateService candidateService;

	@Override
	protected SearchResult<Candidate> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter) {
		return candidateService.search(searchText, page, sortField, searchFilter);
	}

	@Override
	protected Candidate find(Long id) {
		return candidateService.findCandidateById(id);
	}

}