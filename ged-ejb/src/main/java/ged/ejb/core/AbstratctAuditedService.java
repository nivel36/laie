package ged.ejb.core;

import java.util.List;

import ged.ejb.core.events.Audited;
import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.model.Dao;

public abstract class AbstratctAuditedService<T extends AuditedEntity<Long>> implements AuditedService<T> {

	@Override
	@Audited(action = Type.DELETE)
	public void delete(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		entity.setDeleted(true);
		doUpdate(entity);
	}

	protected void doInsert(final T entity) {
		this.getDao().insert(entity);
	}

	protected T doUpdate(final T entity) {
		return this.getDao().update(entity);
	}

	@Override
	public T find(final Long id) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		return this.getDao().find(id);
	}

	@Override
	public List<T> findAll() {
		return this.getDao().findAll();
	}

	protected abstract Dao<Long, T> getDao();

	@Override
	@Audited(action = Type.INSERT)
	public void insert(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		doInsert(entity);
	}

	@Override
	@Audited(action = Type.INSERT)
	public T insertOrUpdate(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		if (entity.getId() == null) {
			doInsert(entity);
			return entity;
		} else {
			return doUpdate(entity);
		}
	}

	@Override
	@Audited(action = Type.UNDELETE)
	public T undelete(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		entity.setDeleted(Boolean.FALSE);
		return doUpdate(entity);
	}

	@Override
	@Audited(action = Type.UPDATE)
	public T update(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		return doUpdate(entity);
	}
}