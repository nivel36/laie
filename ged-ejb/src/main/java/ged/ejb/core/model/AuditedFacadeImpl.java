package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import ged.ejb.core.model.event.PostDelete;
import ged.ejb.core.model.event.PostInsert;
import ged.ejb.core.model.event.PostSoftDelete;
import ged.ejb.core.model.event.PostUndelete;
import ged.ejb.core.model.event.PreDelete;
import ged.ejb.core.model.event.PreInsert;
import ged.ejb.core.model.event.PreSoftDelete;
import ged.ejb.core.model.event.PreUndelete;
import ged.ejb.core.model.event.PreUpdate;

public class AuditedFacadeImpl implements AuditedFacade {

	@PreInsert
	@Inject
	private Event<AbstractEntity> preInsertEvent;

	@PostInsert
	@Inject
	private Event<AbstractEntity> postInsertEvent;

	@ged.ejb.core.model.event.PostUpdate
	@Inject
	private Event<AbstractEntity> postUpdateEvent;

	@PreUpdate
	@Inject
	private Event<AbstractEntity> preUpdateEvent;

	@PreDelete
	@Inject
	private Event<AbstractEntity> preDeleteEvent;

	@PostDelete
	@Inject
	private Event<AbstractEntity> postDeleteEvent;

	@PreUndelete
	@Inject
	private Event<AuditedEntity> preUndeleteEvent;

	@PostUndelete
	@Inject
	private Event<AuditedEntity> postUndeleteEvent;

	@PreSoftDelete
	@Inject
	private Event<AuditedEntity> preSoftDeleteEvent;

	@PostSoftDelete
	@Inject
	private Event<AuditedEntity> postSoftDeleteEvent;

	@Inject
	private PersistenceFacade persistenceFacade;

	@Inject
	private Logger logger;

	@Override
	public void clear() {
		persistenceFacade.clear();
	}

	@Override
	public <T extends AbstractEntity> boolean contains(T entity) {
		return persistenceFacade.contains(entity);
	}

	@Override
	public <T extends AbstractEntity> void delete(T entity) {
		preDeleteEvent.fire(entity);
		persistenceFacade.delete(entity);
		postDeleteEvent.fire(entity);
	}

	@Override
	public <T extends AbstractEntity> void detach(T entity) {
		persistenceFacade.detach(entity);
	}

	@Override
	public void flush() {
		persistenceFacade.flush();
	}

	@Override
	public <T> List<T> getByCriteria(CriteriaQuery<T> cq, int pageSize, int pageNum) {
		return persistenceFacade.getByCriteria(cq, pageSize, pageNum);
	}

	@Override
	public <T> T getByCriteriaSingleResult(CriteriaQuery<T> cq) {
		return persistenceFacade.getByCriteriaSingleResult(cq);
	}

	@Override
	public <T extends AbstractEntity> T getByPrimaryKey(Class<T> entityClass, Long id) {
		return persistenceFacade.getByPrimaryKey(entityClass, id);
	}

	@Override
	public List<?> getByQuery(String nombreQuery, Map<String, Object> parameters, int pageSize, int pageNum) {
		return persistenceFacade.getByQuery(nombreQuery, parameters, pageSize, pageNum);
	}

	@Override
	public Object getByQuerySingleResult(String nombreQuery, Map<String, Object> parameters) {
		return getByQuerySingleResult(nombreQuery, parameters);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum) {
		return persistenceFacade.getByTypedQuery(entityClass, namedQuery, parameters, pageSize, pageNum);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters) {
		return persistenceFacade.getByTypedQuerySingleResult(entityClass, namedQuery, parameters);
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return persistenceFacade.getCriteriaBuilder();
	}

	@Override
	public <T extends AbstractEntity> T getReference(Class<T> entityClass, Object primaryKey) {
		return persistenceFacade.getReference(entityClass, primaryKey);
	}

	@Override
	public <T extends AbstractEntity> void lock(T entity, LockModeType lockModeType, Map<String, Object> properties) {
		persistenceFacade.lock(entity, lockModeType, properties);
	}

	@Override
	public <T extends AbstractEntity> void insert(T entity) {
		logger.log(Level.FINE, "Persis {}", entity.getClass().getCanonicalName());
		preInsertEvent.fire(entity);
		persistenceFacade.insert(entity);
		postInsertEvent.fire(entity);
	}

	@Override
	public <T extends AbstractEntity> void refresh(T entity, LockModeType lockModeType,
			Map<String, Object> properties) {
		persistenceFacade.refresh(entity, lockModeType, properties);
	}

	@Override
	public void setLog(Logger log) {
		this.logger = log;
	}

	@Override
	public void setPersistenceFacade(PersistenceFacadeImpl persistenceFacade) {
		this.persistenceFacade = persistenceFacade;
	}

	@Override
	public void setPostDeleteEvent(Event<AbstractEntity> postDeleteEvent) {
		this.postDeleteEvent = postDeleteEvent;
	}

	@Override
	public void setPostInsertEvent(Event<AbstractEntity> postInsertEvent) {
		this.postInsertEvent = postInsertEvent;
	}

	@Override
	public void setPostSoftDeleteEvent(Event<AuditedEntity> postSoftDeleteEvent) {
		this.postSoftDeleteEvent = postSoftDeleteEvent;
	}

	@Override
	public void setPostUndeleteEvent(Event<AuditedEntity> postUndeleteEvent) {
		this.postUndeleteEvent = postUndeleteEvent;
	}

	@Override
	public void setPostUpdateEvent(Event<AbstractEntity> postUpdateEvent) {
		this.postUpdateEvent = postUpdateEvent;
	}

	@Override
	public void setPreDeleteEvent(Event<AbstractEntity> preDeleteEvent) {
		this.preDeleteEvent = preDeleteEvent;
	}

	@Override
	public void setPreInsertEvent(Event<AbstractEntity> preInsertEvent) {
		this.preInsertEvent = preInsertEvent;
	}

	@Override
	public void setPreSoftDeleteEvent(Event<AuditedEntity> preSoftDeleteEvent) {
		this.preSoftDeleteEvent = preSoftDeleteEvent;
	}

	@Override
	public void setPreUndeleteEvent(Event<AuditedEntity> preUndeleteEvent) {
		this.preUndeleteEvent = preUndeleteEvent;
	}

	@Override
	public void setPreUpdateEvent(Event<AbstractEntity> preUpdateEvent) {
		this.preUpdateEvent = preUpdateEvent;
	}

	@Override
	public <T extends AuditedEntity> void softDelete(T entity) {
		preSoftDeleteEvent.fire(entity);
		entity.setDeleted(true);
		postSoftDeleteEvent.fire(entity);
	}

	@Override
	public <T extends AuditedEntity> void undelete(T entity) {
		preSoftDeleteEvent.fire(entity);
		entity.setDeleted(false);
		postSoftDeleteEvent.fire(entity);
	}

	@Override
	public <T extends AbstractEntity> T update(T entity) {
		preUpdateEvent.fire(entity);
		T mergedEntity = persistenceFacade.update(entity);
		postUpdateEvent.fire(entity);
		return mergedEntity;
	}

	@Override
	public <T extends AbstractEntity> List<T> getAll(Class<T> clazz) {
		return persistenceFacade.getAll(clazz);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByProperties(Class<T> clazz, Map<String, Object> properties,
			int pageSize, int pageNum) {

		return persistenceFacade.getByProperties(clazz, properties, pageSize, pageNum);
	}

	@Override
	public <T extends AbstractEntity> T getByProperties(Class<T> clazz, Map<String, Object> properties) {
		return persistenceFacade.getByProperties(clazz, properties);
	}
}
