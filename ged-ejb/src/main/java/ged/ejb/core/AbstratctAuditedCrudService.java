package ged.ejb.core;

import java.util.List;

import ged.ejb.core.action.Action;
import ged.ejb.core.action.Audited;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.model.CrudDao;

public abstract class AbstratctAuditedCrudService<T extends AuditedEntity> extends AbstractService<Long, T>
		implements AuditedCrudService<T> {

	@Override
	@Audited(action = Action.DELETE)
	public void delete(final T entity) {
		super.delete(entity);
	}

	@Override
	public T find(final Long id) {
		return super.find(id);
	}

	@Override
	public List<T> findAll() {
		return super.findAll();
	}

	@Override
	public abstract CrudDao<Long, T> getDao();

	@Override
	@Audited(action = Action.INSERT)
	public void insert(final T entity) {
		super.insert(entity);
	}

	@Override
	@Audited(action = Action.UNDELETE)
	public T undelete(final T entity) {
		entity.setDeleted(Boolean.FALSE);
		return super.update(entity);
	}

	@Override
	@Audited(action = Action.UNDELETE)
	public T update(final T entity) {
		return super.update(entity);
	}
}