package ged.ejb.core;

import ged.ejb.core.model.AuditedEntity;

public interface AuditedService<T extends AuditedEntity> extends Service<Long, T> {

	T undelete(T entity);

}