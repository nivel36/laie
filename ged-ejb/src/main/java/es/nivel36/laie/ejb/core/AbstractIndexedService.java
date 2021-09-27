package es.nivel36.laie.ejb.core;

import es.nivel36.laie.ejb.core.model.AbstractIndexedDao;
import es.nivel36.laie.ejb.core.model.AbstractIndexedEntity;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;

public abstract class AbstractIndexedService<T extends AbstractIndexedEntity> extends AbstractService<T> {
	
	public SearchResult<T> search(final String searchText, final Page page) {
		return this.getDao().search(searchText, page);
	}

	public SearchResult<T> search(final String searchText, final Page page, final SortField sortField,
			final SearchFacets searchFacets) {
		return this.getDao().search(searchText, page, sortField, searchFacets);
	}
	
	protected abstract AbstractIndexedDao<T> getDao();

}
