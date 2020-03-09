package ged.ejb.core;

import java.util.List;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.AbstractIndexedEntity;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.search.SearchFacets;
import ged.ejb.core.model.search.SearchResult;
import ged.ejb.core.model.search.SortField;

public abstract class AbstractIndexedService<T extends AbstractIndexedEntity> extends AbstractService<T> {
	
	public SearchResult<T> search(final String searchText, final Page page) {
		return this.getDao().search(searchText, page);
	}

	public SearchResult<T> search(final String searchText, final Page page, final List<SortField> sortFields,
			final SearchFacets searchFacets) {
		return this.getDao().search(searchText, page, sortFields, searchFacets);
	}

	public SearchResult<T> search(final String searchText, final Page page, final SortField sortField,
			final SearchFacets searchFacets) {
		return this.getDao().search(searchText, page, sortField, searchFacets);
	}
	
	protected abstract AbstractIndexedDao<T> getDao();

}
