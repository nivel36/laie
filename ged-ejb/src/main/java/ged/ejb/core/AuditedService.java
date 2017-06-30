package ged.ejb.core;

import ged.ejb.core.model.AbstractAuditedEntity;

public interface AuditedService<T extends AbstractAuditedEntity> extends Service<T> {

	T undelete(T entity);
}