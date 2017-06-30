package ged.ejb.core;

import java.util.Objects;

import ged.ejb.core.events.Audited;
import ged.ejb.core.events.Audited.Type;
import ged.ejb.core.model.AuditedEntity;

public abstract class AbstratctAuditedService<T extends AuditedEntity> extends AbstractService<T>
		implements AuditedService<T> {

	@Override
	@Audited(action = Type.DELETE)
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		entity.setDeleted(true);
		save(entity);
	}

	@Override
	@Audited(action = Type.PERSIST)
	public T save(final T entity) {
		Objects.requireNonNull(entity);
		return super.save(entity);
	}

	@Override
	@Audited(action = Type.UNDELETE)
	public T undelete(final T entity) {
		Objects.requireNonNull(entity);
		entity.setDeleted(Boolean.FALSE);
		return save(entity);
	}
}