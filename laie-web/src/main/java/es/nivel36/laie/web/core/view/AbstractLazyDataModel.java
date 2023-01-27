package es.nivel36.laie.web.core.view;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.model.Identifiable;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;

public abstract class AbstractLazyDataModel<T extends Identifiable> extends LazyDataModel<T> {

	private static final long serialVersionUID = -7266573501998556478L;

	protected String searchText;

	@Override
	public T getRowData(final String rowKey) {
		Objects.requireNonNull(rowKey, "RowKey can't be null");
		final Long id = Long.valueOf(rowKey);
		return find(id);
	}

	@Override
	public String getRowKey(final T entity) {
		return getKey(entity);
	}

	protected abstract SearchResult<T> search(String searchText, Page page, SortField sortField, String[] searchFacets);

	protected abstract T find(Long rowkey);

	protected String getKey(T entity) {
		return String.valueOf(entity.getId());
	}

	@Override
	public List<T> load(final int first, final int pageSize, final Map<String, SortMeta> sorts,
			final Map<String, FilterMeta> filters) {
		final Page page = new Page(first, pageSize);
		final SearchResult<T> searchResult = search(this.searchText, page, null, null);
		int numberOfResults = (int) searchResult.total().hitCount();
		this.recalculateFirst(first, pageSize, numberOfResults);
		this.setRowCount(numberOfResults);
		return searchResult.hits();
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 0;
	}

}
