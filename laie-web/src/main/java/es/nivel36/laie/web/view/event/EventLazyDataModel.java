package es.nivel36.laie.web.view.event;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.event.JobCandidatureEventDto;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class EventLazyDataModel extends AbstractLazyDataModel<JobCandidatureEventDto> {

	@Override
	protected SearchResult<JobCandidatureEventDto> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter) {
		return null;
	}

	@Override
	protected JobCandidatureEventDto find(String rowkey) {
		return null;
	}

	@Override
	protected String getKey(JobCandidatureEventDto entity) {
		return null;
	}

}
