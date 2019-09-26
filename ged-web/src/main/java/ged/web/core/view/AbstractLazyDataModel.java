package ged.web.core.view;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.SearchFilter;
import ged.ejb.core.model.SearchFilters;
import ged.ejb.core.model.SearchResult;
import ged.ejb.core.model.SortField;

public abstract class AbstractLazyDataModel<T extends AbstractEntity> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;

	protected String searchText;
	
	protected SearchFilters searchFilter = new SearchFilters();
	
	public void addSearchFilter(String field, String value) {
		searchFilter.addFilter(new SearchFilter(field, value));
	}

	@Override
	public T getRowData(final String rowKey) {
		Objects.requireNonNull(rowKey, "RowKey can't be null");
		return getService().find(Integer.parseInt(rowKey));
	}

	@Override
	public Object getRowKey(final T entity) {
		Objects.requireNonNull(entity, "Entity can't be null");
		return entity.getId();
	}

	protected abstract AbstractService<T> getService();

	@Override
	public List<T> load(final int first, final int pageSize, final String sortFieldName, final SortOrder sortOrder,
			Map<String, Object> filters) {
		final Page page = new Page(first, pageSize);
		final SortField sortField;
		if (sortOrder != null) {
			sortField = new SortField(sortFieldName, sortOrder == SortOrder.DESCENDING);
		} else {
			sortField = null;
		}
		final SearchResult<T> searchResult = getService().search(searchText, page, sortField, searchFilter);
		this.setRowCount(searchResult.getCount());
		return searchResult.getResultData();
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}
}
