package es.nivel36.laie.web.core.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacet;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;

public abstract class AbstractLazyDataModel<T extends Serializable> extends LazyDataModel<T> {

	private static final long serialVersionUID = -7266573501998556478L;

	protected transient SearchFacets searchFilter = new SearchFacets();

	protected String searchText;

	public void addSearchFilter(final String field, final String value, final String... selectedFacets) {
		final SearchFacet searchFacet = new SearchFacet(field, value);
		searchFacet.selectFacets(selectedFacets);
		this.searchFilter.addFacet(searchFacet);
	}

	public void clearSearchFilters() {
		this.searchFilter.clear();
	}

	@Override
	public T getRowData(final String rowKey) {
		Objects.requireNonNull(rowKey, "RowKey can't be null");
		return find(rowKey);
	}

	@Override
	public String getRowKey(final T entity) {
		return getKey(entity);
	}

	protected abstract SearchResult<T> search(String searchText, Page page, SortField sortField,
			SearchFacets searchFilter);

	protected abstract T find(String rowkey);
	
	protected abstract String getKey(T entity);

	@Override
	public List<T> load(final int first, final int pageSize, final Map<String, SortMeta> sorts,
			final Map<String, FilterMeta> filters) {
		final Page page = new Page(first, pageSize);
		SortField sortField = null;
		if (sorts != null && !sorts.isEmpty()) {
			for (SortMeta sort : sorts.values()) {
				sortField = new SortField(sort.getField(), sort.getOrder().isAscending());
				break;
			}
		}
		final SearchResult<T> searchResult = search(this.searchText, page, sortField, this.searchFilter);
		this.recalculateFirst(first, pageSize, searchResult.getCount());
		this.setRowCount(searchResult.getCount());
		return searchResult.getResultData();
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}
