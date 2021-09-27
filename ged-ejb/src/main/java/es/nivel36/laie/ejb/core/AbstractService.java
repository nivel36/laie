package es.nivel36.laie.ejb.core;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.AbstractEntity;
import es.nivel36.laie.ejb.core.model.Page;

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
}