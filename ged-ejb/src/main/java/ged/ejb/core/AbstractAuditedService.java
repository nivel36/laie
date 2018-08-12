package ged.ejb.core;

import java.util.Objects;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.AbstractAuditedEntity;

public abstract class AbstractAuditedService<T extends AbstractAuditedEntity> extends AbstractService<T>
		implements AuditedService<T> {

	@Override
	@Audited(action = ActionType.DELETE)
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		entity.setDeleted(true);
		super.update(entity);
	}

	@Override
	@Audited(action = ActionType.INSERT)
	public void insert(final T entity) {
		super.insert(entity);
	}

	@Override
	@Audited(action = ActionType.UNDELETE)
	public T undelete(final T entity) {
		Objects.requireNonNull(entity);
		entity.setDeleted(Boolean.FALSE);
		return super.update(entity);
	}

	@Override
	@Audited(action = ActionType.UPDATE)
	public T update(final T entity) {
		return super.update(entity);
	}
}