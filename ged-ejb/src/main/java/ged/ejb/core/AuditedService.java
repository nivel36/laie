package ged.ejb.core;

import ged.ejb.core.model.AuditedEntity;

public interface AuditedService<K, T extends AuditedEntity<K>> extends Service<K, T> {

	T undelete(T entity);
}