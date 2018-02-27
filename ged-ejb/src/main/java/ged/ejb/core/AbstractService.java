package ged.ejb.core;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Identificable;

public abstract class AbstractService<T extends Identificable> implements Service<T> {

	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		this.getDao().delete(entity);
	}

	@Override
	public T find(final long id) {
		Objects.requireNonNull(id);
		return this.getDao().find(id);
	}

	@Override
	public List<T> findAll() {
		return this.getDao().findAll();
	}

	protected abstract Dao<T> getDao();

	@Override
	public void insert(final T entity) {
		Objects.requireNonNull(entity);
		this.getDao().insert(entity);
	}

	@Override
	public List<T> search(final String searchText) {
		return this.getDao().search(searchText);
	}

	@Override
	public T update(final T entity) {
		Objects.requireNonNull(entity);
		return this.getDao().update(entity);
	}
}