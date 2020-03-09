package ged.ejb.core.model;

import java.util.ArrayList;
import java.util.List;

import ged.ejb.core.model.search.SearchFacets;
import ged.ejb.core.model.search.SearchResult;
import ged.ejb.core.model.search.SortField;

public abstract class AbstractIndexedDao<T extends AbstractIndexedEntity> extends AbstractDao<T> {

	public SearchResult<T> search(final String searchText, final Page page) {
		return this.search(searchText, page, new ArrayList<SortField>(), null);
	}

	public SearchResult<T> search(final String searchText, final Page page, final List<SortField> sortOrders,
			final SearchFacets searchFacets) {
		return this.getPersistenceFacade().search(this.getType(), page, sortOrders, searchFacets, searchText,
				this.searchFields());
	}

	public SearchResult<T> search(final String searchText, final Page page, final SortField sortOrder,
			final SearchFacets searchFacets) {
		final List<SortField> sortOrders = new ArrayList<>();
		if (sortOrder != null) {
			sortOrders.add(sortOrder);
		}
		return this.search(searchText, page, sortOrders, searchFacets);
	}

	public abstract String[] searchFields();

}
