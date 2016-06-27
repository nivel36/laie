package ged.ejb.core;

import ged.ejb.core.model.AuditedEntity;

public interface AuditedCrudService<T extends AuditedEntity> extends CrudService<Long, T> {

	T undelete(T entity);

}