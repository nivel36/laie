package es.nivel36.laie.web.view.event;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.event.JobCandidatureEvent;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class EventLazyDataModel extends AbstractLazyDataModel<JobCandidatureEvent> {

	private static final long serialVersionUID = 4783066577882412827L;

	@Override
	protected SearchResult<JobCandidatureEvent> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter) {
		return null;
	}

	@Override
	protected JobCandidatureEvent find(Long rowkey) {
		return null;
	}
}
