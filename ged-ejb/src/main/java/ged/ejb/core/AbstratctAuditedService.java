package ged.ejb.core;

import java.util.List;

import ged.ejb.core.action.Action;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.model.Dao;

public abstract class AbstratctAuditedService<T extends AuditedEntity<Long>> extends AbstractService<Long, T>
		implements AuditedService<T> {

	@Override
	@Audited(action = Action.DELETE)
	public void delete(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		super.delete(entity);
	}

	@Override
	public T find(final Long id) {
		if (id == null) {
			throw new NullPointerException();
		}
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		return super.find(id);
	}

	@Override
	public List<T> findAll() {
		return super.findAll();
	}

	@Override
	public abstract Dao<Long, T> getDao();

	@Override
	@Audited(action = Action.INSERT)
	public void insert(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		super.insert(entity);
	}

	@Override
	@Audited(action = Action.UNDELETE)
	public T undelete(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		entity.setDeleted(Boolean.FALSE);
		return super.update(entity);
	}

	@Override
	@Audited(action = Action.UNDELETE)
	public T update(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		return super.update(entity);
	}
}