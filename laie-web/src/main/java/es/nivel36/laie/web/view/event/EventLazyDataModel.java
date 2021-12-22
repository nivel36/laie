package es.nivel36.laie.web.view.event;

import java.util.Map;

import org.primefaces.model.FilterMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.event.JobCandidatureEventDto;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class EventLazyDataModel extends AbstractLazyDataModel<JobCandidatureEventDto> {

	private static final long serialVersionUID = 4783066577882412827L;

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

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}
}
