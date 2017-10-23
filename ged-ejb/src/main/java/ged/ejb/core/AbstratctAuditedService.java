package ged.ejb.core;

import java.util.Objects;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.AbstractAuditedEntity;

public abstract class AbstratctAuditedService<T extends AbstractAuditedEntity> extends AbstractService<T>
		implements AuditedService<T> {

	@Override
	@Audited(action = ActionType.DELETE)
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		entity.setDeleted(true);
		super.save(entity);
	}

	@Override
	@Audited(action = ActionType.SAVE)
	public T save(final T entity) {
		return super.save(entity);
	}

	@Override
	@Audited(action = ActionType.UNDELETE)
	public T undelete(final T entity) {
		Objects.requireNonNull(entity);
		entity.setDeleted(Boolean.FALSE);
		return super.save(entity);
	}
}