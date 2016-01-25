package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PersistenceFacadeImpl implements PersistenceFacade {

	@Inject
	private Logger logger;

	@Inject
	private EntityManager em;

	/**
	 * El número máximo de resultados que permiten las búsquedas
	 */
	private final static int RES_LIMIT = 150;

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#clear()
	 */
	@Override
	public void clear() {
		logger.log(Level.FINE, "Clear forzado");
		em.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#contains(T)
	 */
	@Override
	public <T extends AbstractEntity> boolean contains(T entity) {
		boolean isContained = em.contains(entity);
		logger.log(Level.FINE, "La entidad {0} esta en el em? {1}",
				new Object[] { entity.getClass().getSimpleName(), isContained });
		return isContained;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#delete(T)
	 */
	@Override
	public <T extends AbstractEntity> void delete(T entity) {
		logger.log(
				Level.FINE,
				"Eliminando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(),
						entity.getId() });
		em.remove(em.merge(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#detach(T)
	 */
	@Override
	public <T extends AbstractEntity> void detach(T entity) {
		logger.log(
				Level.FINE,
				"Desincronizando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(),
						entity.getId() });
		em.detach(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#flush()
	 */
	@Override
	public void flush() {
		logger.log(Level.FINE, "Flush forzado");
		em.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * afersa.core.model.PersistenceFacade#getByCriteria(javax.persistence.criteria
	 * .CriteriaQuery, int, int)
	 */
	@Override
	public <T> List<T> getByCriteria(CriteriaQuery<T> cq, int pageSize,
			int pageNum) {
		logger.log(Level.FINE, "Lanzando criteria");
		TypedQuery<T> query = em.createQuery(cq);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#getByCriteriaSingleResult(javax.
	 * persistence.criteria.CriteriaQuery)
	 */
	@Override
	public <T> T getByCriteriaSingleResult(CriteriaQuery<T> cq) {
		logger.log(Level.FINE, "Lanzando criteria para un solo resultado");
		TypedQuery<T> query = em.createQuery(cq);
		return query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#getByPrimaryKey(java.lang.Class,
	 * java.lang.Object)
	 */
	@Override
	public <T extends AbstractEntity> T getByPrimaryKey(Class<T> entityClass,
			Long id) {
		logger.log(Level.FINE,
				"Buscando por clave primaria::clase={0}::id={1}", new Object[] {
						entityClass, id });
		return em.find(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#getByQuery(java.lang.String,
	 * java.util.Map, int, int)
	 */
	@Override
	public List<?> getByQuery(String nombreQuery,
			Map<String, Object> parameters, int pageSize, int pageNum) {
		logger.log(Level.FINE, "Lanzando la getByQuery {0}", nombreQuery);
		Query query = em.createNamedQuery(nombreQuery);
		paginar(pageSize, pageNum, query);
		parametrizar(parameters, query);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * afersa.core.model.PersistenceFacade#getByQuerySingleResult(java.lang.
	 * String, java.util.Map)
	 */
	@Override
	public Object getByQuerySingleResult(String nombreQuery,
			Map<String, Object> parameters) {
		logger.log(Level.FINE, "Lanzando la la getByQuerySingleResult {0} ",
				nombreQuery);
		Query query = em.createNamedQuery(nombreQuery);
		parametrizar(parameters, query);
		Object result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#getByTypedQuery(java.lang.Class,
	 * java.lang.String, java.util.Map, int, int)
	 */
	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum) {
		logger.log(Level.FINE, "Ejecutando la getByTypedQuery: {0}", namedQuery);
		TypedQuery<T> query = em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * afersa.core.model.PersistenceFacade#getByTypedQuerySingleResult(java.
	 * lang.Class, java.lang.String, java.util.Map)
	 */
	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters) {
		logger.log(Level.FINE,
				"Ejecutando la getByTypedQuerySingleResult: {0}", namedQuery);
		TypedQuery<T> query = em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		T result = query.getSingleResult();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#getCriteriaBuilder()
	 */
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return em.getCriteriaBuilder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#getReference(java.lang.Class,
	 * java.lang.Object)
	 */
	@Override
	public <T extends AbstractEntity> T getReference(Class<T> entityClass,
			Object primaryKey) {
		logger.log(Level.FINE, "Reference de la entidad::Clase={0}::Id={1}",
				new Object[] { entityClass, primaryKey });
		return em.getReference(entityClass, primaryKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#lock(T,
	 * javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public <T extends AbstractEntity> void lock(T entity,
			LockModeType lockModeType, Map<String, Object> properties) {
		logger.log(
				Level.FINE,
				"Bloqueando la entidad::clase={0}::id={1}::lockMode={2}",
				new Object[] { entity.getClass().getCanonicalName(),
						entity.getId(), lockModeType });
		em.lock(entity, lockModeType, properties);
	}

	private void paginar(int pageSize, int pageNum, Query query) {
		if (pageNum < 0) {
			throw new IllegalArgumentException("pageNum: " + pageNum);
		}

		if (pageSize < 0) {
			throw new IllegalArgumentException("pageSize: " + pageSize);
		}

		logger.log(Level.FINEST, "Página actual {0}", pageNum);
		query.setFirstResult(pageNum * pageSize);

		if (pageSize > 0) {
			logger.log(Level.FINEST, "Tamaño de página {0}", pageSize);
			query.setMaxResults(pageSize);
		} else if (pageSize == 0) {
			logger.log(Level.FINEST, "Limitando a {0} resultados", RES_LIMIT);
			query.setMaxResults(RES_LIMIT);
		}
	}

	private void parametrizar(Map<String, Object> parameters, Query query) {
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				Object parameter = parameters.get(key);
				logger.log(Level.FINEST, "key={0}::parameter={1}",
						new Object[] { key, parameter });
				query.setParameter(key, parameter);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#persist(T)
	 */
	@Override
	public <T extends AbstractEntity> void insert(T entity) {
		logger.log(Level.FINE,
				"Insertando una nueva entidad de tipod::Clase={0}", entity
						.getClass().getCanonicalName());
		em.persist(entity);
		logger.log(Level.FINE, "Se le ha asignado la id={0}", entity.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#refresh(T,
	 * javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public <T extends AbstractEntity> void refresh(T entity,
			LockModeType lockModeType, Map<String, Object> properties) {
		logger.log(
				Level.FINE,
				"Refresh de la entidad::clase={0}::id={1}::lockMode={2}",
				new Object[] { entity.getClass().getCanonicalName(),
						entity.getId(), lockModeType });
		em.refresh(entity, lockModeType, properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * afersa.core.model.PersistenceFacade#setEm(javax.persistence.EntityManager
	 * )
	 */
	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * afersa.core.model.PersistenceFacade#setLogger(java.util.logging.Logger)
	 */
	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see afersa.core.model.PersistenceFacade#update(T)
	 */
	@Override
	public <T extends AbstractEntity> T update(T entity) {
		logger.log(
				Level.FINE,
				"Actualizando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(),
						entity.getId() });
		if (!em.contains(entity)) {
			return em.merge(entity);
		} else {
			return entity;
		}
	}

	private <T extends AbstractEntity> TypedQuery<T> createQueryFromCriteria(
			Class<T> clazz, Map<String, Object> properties) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		CriteriaQuery<T> criteriaQuery = cq.select(root);

		if (properties != null && !properties.isEmpty()) {
			Set<String> keys = properties.keySet();
			Predicate condition = null;
			for (String key : keys) {
				if (condition == null) {
					condition = cb.equal(root.get(key), properties.get(key));
				} else {
					condition = cb.and(condition);
				}
			}
			criteriaQuery.where(condition);
		}

		TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery;
	}

	@Override
	public <T extends AbstractEntity> List<T> getAll(Class<T> clazz) {
		TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, null);
		paginar(0, RES_LIMIT, typedQuery);
		return typedQuery.getResultList();
	}

	@Override
	public <T extends AbstractEntity> List<T> getByProperties(Class<T> clazz,
			Map<String, Object> properties, int pageSize, int pageNum) {
		TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, properties);
		paginar(pageSize, pageNum, typedQuery);
		return typedQuery.getResultList();
	}

	@Override
	public <T extends AbstractEntity> T getByProperties(Class<T> clazz,
			Map<String, Object> properties) {
		TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, properties);
		parametrizar(properties, typedQuery);
		return typedQuery.getSingleResult();
	}
}
