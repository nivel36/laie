package ged.web.core.view;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import ged.ejb.core.AbstractIndexedService;
import ged.ejb.core.model.AbstractIndexedEntity;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.search.SearchFacet;
import ged.ejb.core.model.search.SearchFacets;
import ged.ejb.core.model.search.SearchResult;
import ged.ejb.core.model.search.SortField;

public abstract class AbstractLazyDataModel<T extends AbstractIndexedEntity> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;

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
		return this.getService().find(Integer.parseInt(rowKey));
	}

	@Override
	public String getRowKey(final T entity) {
		Objects.requireNonNull(entity, "Entity can't be null");
		return String.valueOf(entity.getId());
	}

	protected abstract AbstractIndexedService<T> getService();

	@Override
	public List<T> load(final int first, final int pageSize, final Map<String, SortMeta> sorts,
			final Map<String, FilterMeta> filters) {
		final Page page = new Page(first, pageSize);
		SortField sortField = null;
		if (sorts != null && !sorts.isEmpty()) {
			for (SortMeta sort : sorts.values()) {
				if(sort.getPriority() == 0) { // only one sort allowed
					sortField = new SortField(sort.getField(), sort.getOrder().isAscending());
					break;
				}
			}
		}
		final SearchResult<T> searchResult = this.getService().search(this.searchText, page, sortField,
				this.searchFilter);
		this.setRowCount(searchResult.getCount());
		return searchResult.getResultData();
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}
