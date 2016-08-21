package ged.ejb.core;

import ged.ejb.core.model.AuditedEntity;

public interface AuditedService<T extends AuditedEntity<Long>> extends Service<Long, T> {

	T insertOrUpdate(T entity);

	T undelete(T entity);
}