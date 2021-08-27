package es.nivel36.laie.core.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.core.service.search.SortField;
import es.nivel36.laie.core.service.search.SortField.SortType;

public abstract class AbstractLazyDataModel<T extends Serializable> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;

	protected String searchText;

	@Override
	public List<T> load(final int first, final int pageSize, final Map<String, SortMeta> sorts,
			final Map<String, FilterMeta> filters) {
		SortField sortField = null;
		if (sorts != null && !sorts.isEmpty()) {
			for (SortMeta sort : sorts.values()) {
				sortField = new SortField(sort.getField(),
						sort.getOrder().isAscending() ? SortType.ASCENDING : SortType.DESCENDING);
				break;
			}
		}
		List<T> results = doSearch(first, pageSize, sortField, searchText);
		this.setRowCount(results.size());
		return results;
	}
	
	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public abstract List<T> doSearch(final int firstResult, final int maxResults, final SortField sortField,
			final String searchText);

}
