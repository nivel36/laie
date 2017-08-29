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

	protected void insert(final T entity) {
		this.getDao().insert(entity);
	}

	protected T update(final T entity) {
		return this.getDao().update(entity);
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
	public T save(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			insert(entity);
			return entity;
		} else {
			return update(entity);
		}
	}
}