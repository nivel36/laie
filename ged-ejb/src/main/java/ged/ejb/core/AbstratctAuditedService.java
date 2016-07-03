package ged.ejb.core;

import java.util.List;

import ged.ejb.core.action.Action;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.model.Dao;

public abstract class AbstratctAuditedService<T extends AuditedEntity<Long>> implements AuditedService<T> {

	@Override
	@Audited(action = Action.DELETE)
	public void delete(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		entity.setDeleted(true);
		this.getDao().update(entity);
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

	public abstract Dao<Long, T> getDao();

	@Override
	@Audited(action = Action.INSERT)
	public void insert(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		this.getDao().insert(entity);
	}

	@Override
	@Audited(action = Action.UNDELETE)
	public T undelete(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		entity.setDeleted(Boolean.FALSE);
		return this.getDao().update(entity);
	}

	@Override
	@Audited(action = Action.UPDATE)
	public T update(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		return this.getDao().update(entity);
	}
}