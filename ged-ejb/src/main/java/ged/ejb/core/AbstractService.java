package ged.ejb.core;

import java.util.List;
import java.util.Objects;

import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Identificable;

public abstract class AbstractService<K, T extends Identificable<K>> implements Service<K, T> {

	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		this.getDao().delete(entity);
	}

	protected void doInsert(final T entity) {
		this.getDao().insert(entity);
	}

	protected T doUpdate(final T entity) {
		return this.getDao().update(entity);
	}

	@Override
	public T find(final K id) {
		Objects.requireNonNull(id);
		return this.getDao().find(id);
	}

	@Override
	public List<T> findAll() {
		return this.getDao().findAll();
	}

	protected abstract Dao<K, T> getDao();

	@Override
	public T save(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == null) {
			doInsert(entity);
			return entity;
		} else {
			return doUpdate(entity);
		}
	}
}