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
	private Logger logger;

	@Inject
	protected AuditedFacade auditedFacade;

	protected void clear() {
		auditedFacade.clear();
	}

	protected <T extends AuditedEntity> boolean contains(T entity) {
		return auditedFacade.contains(entity);
	}

	@Override
	public <T extends AbstractEntity> void delete(T entity) {
		auditedFacade.delete(entity);
	}

	protected <T extends AuditedEntity> void detach(T entity) {
		auditedFacade.detach(entity);
	}

	protected void flush() {
		auditedFacade.flush();
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return auditedFacade.getCriteriaBuilder();
	}

	protected <T extends AuditedEntity> T getReference(Class<T> entityClass,
			Object primaryKey) {
		return auditedFacade.getReference(entityClass, primaryKey);
	}

	@Override
	public <T extends AbstractEntity> void insert(T entity) {
		auditedFacade.insert(entity);
	}

	protected <T extends AuditedEntity> void lock(T entity,
			LockModeType lockModeType, Map<String, Object> properties) {
		auditedFacade.lock(entity, lockModeType, properties);
	}

	@Override
	public <T extends AbstractEntity> T update(T entity) {
		return auditedFacade.update(entity);
	}

	protected void refresh(AuditedEntity entity) {
		refresh(entity, null, null);
	}

	protected <T extends AuditedEntity> void refresh(T entity,
			LockModeType lockModeType, Map<String, Object> properties) {
		auditedFacade.refresh(entity, lockModeType, properties);
	}

	protected <T> List<T> getByCriteria(CriteriaQuery<T> cq) {
		return getByCriteria(cq, 0, 0);
	}

	protected <T> List<T> getByCriteria(CriteriaQuery<T> cq, int pageSize,
			int pageNum) {
		return auditedFacade.getByCriteria(cq, pageSize, pageNum);
	}

	protected <T> T getByCriteriaSingleResult(CriteriaQuery<T> cq) {
		return auditedFacade.getByCriteriaSingleResult(cq);
	}

	@Override
	public <T extends AbstractEntity> T getByPrimaryKey(Class<T> entityClass,
			Long id) {
		return auditedFacade.getByPrimaryKey(entityClass, id);
	}

	@Override
	public List<?> getByQuery(String nombreQuery, Map<String, Object> parameters) {
		return getByQuery(nombreQuery, parameters, 0, 0);
	}

	@Override
	public List<?> getByQuery(String nombreQuery,
			Map<String, Object> parameters, int pageSize, int pageNum) {
		return auditedFacade.getByQuery(nombreQuery, parameters, pageSize,
				pageNum);
	}

	@Override
	public Object getByQuerySingleResult(String nombreQuery) {
		return getByQuerySingleResult(nombreQuery, null);
	}

	@Override
	public Object getByQuerySingleResult(String nombreQuery,
			Map<String, Object> parameters) {
		return auditedFacade.getByQuerySingleResult(nombreQuery, parameters);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters) {
		return getByTypedQuery(entityClass, namedQuery, parameters, 0, 0);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum) {
		return auditedFacade.getByTypedQuery(entityClass, namedQuery,
				parameters, pageSize, pageNum);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery) {
		return getByTypedQuerySingleResult(entityClass, namedQuery, null);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters) {
		return auditedFacade.getByTypedQuerySingleResult(entityClass,
				namedQuery, parameters);
	}

	@Override
	public <T extends AuditedEntity> void softDelete(T entity) {
		logger.log(
				Level.FINE,
				"Eliminación lógica de la entidad::Clase={}::Id={}",
				new Object[] { entity.getClass().getCanonicalName(),
						entity.getId() });
		auditedFacade.softDelete(entity);
	}

	@Override
	public <T extends AuditedEntity> void undelete(T entity) {
		logger.log(
				Level.FINE,
				"Restaurando la entidad::Clase={}::Id={}",
				new Object[] { entity.getClass().getCanonicalName(),
						entity.getId() });
		auditedFacade.undelete(entity);
	}

	@Override
	public <T extends AbstractEntity> List<T> getAll(Class<T> clazz) {
		return auditedFacade.getAll(clazz);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByProperties(Class<T> clazz,
			Map<String, Object> properties, int pageSize, int pageNum) {
		return auditedFacade.getByProperties(clazz, properties, pageSize,
				pageNum);
	}

	@Override
	public <T extends AbstractEntity> T getByProperties(Class<T> clazz,
			Map<String, Object> properties) {
		return auditedFacade.getByProperties(clazz, properties);
	}
}
