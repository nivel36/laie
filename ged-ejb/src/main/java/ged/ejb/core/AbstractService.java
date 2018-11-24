package ged.ejb.core;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.Page;

public abstract class AbstractService<T extends AbstractEntity> {

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
		return this.getDao().findAll();
	}

	protected abstract AbstractDao<T> getDao();

	public T save(final T entity) {
		Objects.requireNonNull(entity);
		return this.getDao().save(entity);
	}

	public List<T> search(final String searchText) {
		return this.getDao().search(searchText);
	}
}