package ged.ejb.core;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.SearchFacets;
import ged.ejb.core.model.SearchResult;
import ged.ejb.core.model.SortField;

public abstract class AbstractService<T extends AbstractEntity> {

	@Resource
	private SessionContext sessionContext;

	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		this.getDao().delete(entity);
	}

	public T find(final long id) {
		Objects.requireNonNull(id);
		return this.getDao().find(id);
	}

	public List<T> findAll(final Page page) {
		Objects.requireNonNull(page);
		return this.getDao().findAll(page);
	}

	protected abstract AbstractDao<T> getDao();

	public T save(final T entity) {
		Objects.requireNonNull(entity);
		return this.getDao().save(entity);
	}

	public SearchResult<T> search(final String searchText, final Page page) {
		return this.getDao().search(searchText, page);
	}

	public SearchResult<T> search(final String searchText, final Page page, final List<SortField> sortFields,
			final SearchFacets searchFacets) {
		return this.getDao().search(searchText, page, sortFields, searchFacets);
	}

	public SearchResult<T> search(final String searchText, final Page page, final SortField SortField,
			final SearchFacets searchFacets) {
		return this.getDao().search(searchText, page, SortField, searchFacets);
	}
}