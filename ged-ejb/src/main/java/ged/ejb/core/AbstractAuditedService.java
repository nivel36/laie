package ged.ejb.core;

import java.util.Objects;

import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.core.security.Securized;

public abstract class AbstractAuditedService<T extends AbstractAuditedEntity> extends AbstractService<T> {

	@Securized
	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		entity.setDeleted(true);
		super.save(entity);
	}

	@Override
	public T save(final T entity) {
		Objects.requireNonNull(entity);
		return super.save(entity);
	}

	@Securized
	public T undelete(final T entity) {
		Objects.requireNonNull(entity);
		entity.setDeleted(Boolean.FALSE);
		return super.save(entity);
	}
}