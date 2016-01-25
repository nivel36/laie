package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.enterprise.event.Event;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Local
public interface AuditedFacade {

	public abstract void clear();

	public abstract <T extends AbstractEntity> boolean contains(T entity);

	public abstract <T extends AbstractEntity> void delete(T entity);

	public abstract <T extends AbstractEntity> void detach(T entity);

	public abstract void flush();

	public abstract <T> List<T> getByCriteria(CriteriaQuery<T> cq,
			int pageSize, int pageNum);

	public abstract <T> T getByCriteriaSingleResult(CriteriaQuery<T> cq);

	public abstract <T extends AbstractEntity> T getByPrimaryKey(
			Class<T> entityClass, Long id);

	public abstract List<?> getByQuery(String nombreQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	public abstract Object getByQuerySingleResult(String nombreQuery,
			Map<String, Object> parameters);

	public abstract <T extends AbstractEntity> List<T> getByTypedQuery(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	public abstract <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);

	public abstract CriteriaBuilder getCriteriaBuilder();

	public abstract <T extends AbstractEntity> T getReference(
			Class<T> entityClass, Object primaryKey);

	public abstract <T extends AbstractEntity> void lock(T entity,
			LockModeType lockModeType, Map<String, Object> properties);

	public abstract <T extends AbstractEntity> void insert(T entity);

	public abstract <T extends AbstractEntity> void refresh(T entity,
			LockModeType lockModeType, Map<String, Object> properties);

	public abstract void setLog(Logger log);

	public abstract void setPersistenceFacade(
			PersistenceFacadeImpl persistenceFacade);

	public abstract void setPostDeleteEvent(Event<AbstractEntity> postDeleteEvent);

	public abstract void setPostInsertEvent(Event<AbstractEntity> postInsertEvent);

	public abstract void setPostSoftDeleteEvent(
			Event<AuditedEntity> postSoftDeleteEvent);

	public abstract void setPostUndeleteEvent(
			Event<AuditedEntity> postUndeleteEvent);

	public abstract void setPostUpdateEvent(Event<AbstractEntity> postUpdateEvent);

	public abstract void setPreDeleteEvent(Event<AbstractEntity> preDeleteEvent);

	public abstract void setPreInsertEvent(Event<AbstractEntity> preInsertEvent);

	public abstract void setPreSoftDeleteEvent(
			Event<AuditedEntity> preSoftDeleteEvent);

	public abstract void setPreUndeleteEvent(
			Event<AuditedEntity> preUndeleteEvent);

	public abstract void setPreUpdateEvent(Event<AbstractEntity> preUpdateEvent);

	public abstract <T extends AuditedEntity> void softDelete(T entity);

	public abstract <T extends AuditedEntity> void undelete(T entity);

	public abstract <T extends AbstractEntity> T update(T entity);

	public abstract <T extends AbstractEntity> List<T> getAll(Class<T> clazz);
	
	public abstract <T extends AbstractEntity> List<T> getByProperties(
			Class<T> clazz, Map<String, Object> properties, int pageSize,
			int pageNum);

	public abstract <T extends AbstractEntity> T getByProperties(
			Class<T> clazz, Map<String, Object> properties);

}