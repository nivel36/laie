package ged.ejb.core;

import ged.ejb.core.model.Auditable;

public interface AuditedService<T extends Auditable> extends Service<T> {

	T undelete(T entity);
}