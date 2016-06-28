package ged.ejb.core;

import java.util.List;

import ged.ejb.core.model.CrudDao;
import ged.ejb.core.model.Entity;

public abstract class AbstractService<K, T extends Entity<K>> implements CrudService<K, T> {

	@Override
	public void delete(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		this.getDao().delete(entity);
	}

	@Override
	public T find(final K id) {
		if (id == null) {
			throw new NullPointerException();
		}
		return this.getDao().find(id);
	}

	@Override
	public List<T> findAll() {
		return this.getDao().findAll();
	}

	protected abstract CrudDao<K, T> getDao();

	@Override
	public void insert(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		this.getDao().insert(entity);
	}

	@Override
	public T update(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		return this.getDao().update(entity);
	}
}