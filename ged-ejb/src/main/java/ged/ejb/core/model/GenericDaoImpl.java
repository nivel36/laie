package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class GenericDaoImpl implements GenericDao {

	@Inject
	protected AuditedFacade auditedFacade;

	@Inject
	private Logger logger;

	protected void clear() {
		this.auditedFacade.clear();
	}

	protected <T extends AuditedEntity> boolean contains(final T entity) {
		return this.auditedFacade.contains(entity);
	}

	@Override
	public <T extends AbstractEntity> void delete(final T entity) {
		this.auditedFacade.delete(entity);
	}

	protected <T extends AuditedEntity> void detach(final T entity) {
		this.auditedFacade.detach(entity);
	}

	protected void flush() {
		this.auditedFacade.flush();
	}

	@Override
	public <T extends AbstractEntity> List<T> getAll(final Class<T> clazz) {
		return this.auditedFacade.getAll(clazz);
	}

	protected <T> List<T> getByCriteria(final CriteriaQuery<T> cq) {
		return getByCriteria(cq, 0, 0);
	}

	protected <T> List<T> getByCriteria(final CriteriaQuery<T> cq, final int pageSize, final int pageNum) {
		return this.auditedFacade.getByCriteria(cq, pageSize, pageNum);
	}

	protected <T> T getByCriteriaSingleResult(final CriteriaQuery<T> cq) {
		return this.auditedFacade.getByCriteriaSingleResult(cq);
	}

	@Override
	public <T extends AbstractEntity> T getByPrimaryKey(final Class<T> entityClass, final Long id) {
		return this.auditedFacade.getByPrimaryKey(entityClass, id);
	}

	@Override
	public <T extends AbstractEntity> T getByProperties(final Class<T> clazz, final Map<String, Object> properties) {
		return this.auditedFacade.getByProperties(clazz, properties);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByProperties(final Class<T> clazz,
			final Map<String, Object> properties, final int pageSize, final int pageNum) {
		return this.auditedFacade.getByProperties(clazz, properties, pageSize, pageNum);
	}

	@Override
	public List<?> getByQuery(final String nombreQuery, final Map<String, Object> parameters) {
		return getByQuery(nombreQuery, parameters, 0, 0);
	}

	@Override
	public List<?> getByQuery(final String nombreQuery, final Map<String, Object> parameters, final int pageSize,
			final int pageNum) {
		return this.auditedFacade.getByQuery(nombreQuery, parameters, pageSize, pageNum);
	}

	@Override
	public Object getByQuerySingleResult(final String nombreQuery) {
		return getByQuerySingleResult(nombreQuery, null);
	}

	@Override
	public Object getByQuerySingleResult(final String nombreQuery, final Map<String, Object> parameters) {
		return this.auditedFacade.getByQuerySingleResult(nombreQuery, parameters);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		return getByTypedQuery(entityClass, namedQuery, parameters, 0, 0);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final int pageSize, final int pageNum) {
		return this.auditedFacade.getByTypedQuery(entityClass, namedQuery, parameters, pageSize, pageNum);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(final Class<T> entityClass,
			final String namedQuery) {
		return getByTypedQuerySingleResult(entityClass, namedQuery, null);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		return this.auditedFacade.getByTypedQuerySingleResult(entityClass, namedQuery, parameters);
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return this.auditedFacade.getCriteriaBuilder();
	}

	protected <T extends AuditedEntity> T getReference(final Class<T> entityClass, final Object primaryKey) {
		return this.auditedFacade.getReference(entityClass, primaryKey);
	}

	@Override
	public <T extends AbstractEntity> void insert(final T entity) {
		this.auditedFacade.insert(entity);
	}

	protected <T extends AuditedEntity> void lock(final T entity, final LockModeType lockModeType,
			final Map<String, Object> properties) {
		this.auditedFacade.lock(entity, lockModeType, properties);
	}

	protected void refresh(final AuditedEntity entity) {
		refresh(entity, null, null);
	}

	protected <T extends AuditedEntity> void refresh(final T entity, final LockModeType lockModeType,
			final Map<String, Object> properties) {
		this.auditedFacade.refresh(entity, lockModeType, properties);
	}

	@Override
	public <T extends AuditedEntity> void softDelete(final T entity) {
		this.logger.log(Level.FINE, "Eliminación lógica de la entidad::Clase={}::Id={}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		this.auditedFacade.softDelete(entity);
	}

	@Override
	public <T extends AuditedEntity> void undelete(final T entity) {
		this.logger.log(Level.FINE, "Restaurando la entidad::Clase={}::Id={}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		this.auditedFacade.undelete(entity);
	}

	@Override
	public <T extends AbstractEntity> T update(final T entity) {
		return this.auditedFacade.update(entity);
	}
}
