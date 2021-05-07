package ged.ejb.core.model;

import ged.ejb.core.model.search.SearchFacets;
import ged.ejb.core.model.search.SearchResult;
import ged.ejb.core.model.search.SortField;

public abstract class AbstractIndexedDao<T extends AbstractIndexedEntity> extends AbstractDao<T> {

	public SearchResult<T> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<T> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		return this.getPersistenceFacade().search(this.getType(), page, sortOrder, searchFacets, searchText,
				this.searchFields());
	}

	public abstract String[] searchFields();

}
