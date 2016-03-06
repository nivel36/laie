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

import ged.ejb.core.Repository;

@Repository
public class PersistenceFacadeImpl implements PersistenceFacade {

	/**
	 * El número máximo de resultados que permiten las búsquedas
	 */
	private final static int RES_LIMIT = 150;

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#clear()
	 */
	@Override
	public void clear() {
		this.logger.log(Level.FINE, "Clear forzado");
		this.em.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#contains(T)
	 */
	@Override
	public <T extends AbstractEntity> boolean contains(final T entity) {
		final boolean isContained = this.em.contains(entity);
		this.logger.log(Level.FINE, "La entidad {0} esta en el em? {1}",
				new Object[] { entity.getClass().getSimpleName(), isContained });
		return isContained;
	}

	private <T extends AbstractEntity> TypedQuery<T> createQueryFromCriteria(final Class<T> clazz,
			final Map<String, Object> properties) {
		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<T> cq = cb.createQuery(clazz);
		final Root<T> root = cq.from(clazz);
		final CriteriaQuery<T> criteriaQuery = cq.select(root);
		if ((properties != null) && !properties.isEmpty()) {
			final Set<String> keys = properties.keySet();
			Predicate condition = null;
			for (final String key : keys) {
				if (condition == null) {
					condition = cb.equal(root.get(key), properties.get(key));
				} else {
					condition = cb.and(condition);
				}
			}
			criteriaQuery.where(condition);
		}
		final TypedQuery<T> typedQuery = this.em.createQuery(criteriaQuery);
		return typedQuery;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#delete(T)
	 */
	@Override
	public <T extends AbstractEntity> void delete(final T entity) {
		this.logger.log(Level.FINE, "Eliminando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		if (this.em.contains(entity)) {
			this.em.remove(entity);
		} else {
			this.em.remove(this.em.merge(entity));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#detach(T)
	 */
	@Override
	public <T extends AbstractEntity> void detach(final T entity) {
		this.logger.log(Level.FINE, "Desincronizando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		this.em.detach(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#flush()
	 */
	@Override
	public void flush() {
		this.logger.log(Level.FINE, "Flush forzado");
		this.em.flush();
	}

	@Override
	public <T extends AbstractEntity> List<T> getAll(final Class<T> clazz) {
		final TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, null);
		paginar(0, RES_LIMIT, typedQuery);
		return typedQuery.getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#getByCriteria(javax.persistence.
	 * criteria .CriteriaQuery, int, int)
	 */
	@Override
	public <T> List<T> getByCriteria(final CriteriaQuery<T> cq, final int pageSize, final int pageNum) {
		this.logger.log(Level.FINE, "Lanzando criteria");
		final TypedQuery<T> query = this.em.createQuery(cq);
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
	public <T> T getByCriteriaSingleResult(final CriteriaQuery<T> cq) {
		this.logger.log(Level.FINE, "Lanzando criteria para un solo resultado");
		final TypedQuery<T> query = this.em.createQuery(cq);
		return query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#getByPrimaryKey(java.lang.Class,
	 * java.lang.Object)
	 */
	@Override
	public <T extends AbstractEntity> T getByPrimaryKey(final Class<T> entityClass, final Long id) {
		this.logger.log(Level.FINE, "Buscando por clave primaria::clase={0}::id={1}", new Object[] { entityClass, id });
		return this.em.find(entityClass, id);
	}

	@Override
	public <T extends AbstractEntity> T getByProperties(final Class<T> clazz, final Map<String, Object> properties) {
		final TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, properties);
		parametrizar(properties, typedQuery);
		return typedQuery.getSingleResult();
	}

	@Override
	public <T extends AbstractEntity> List<T> getByProperties(final Class<T> clazz,
			final Map<String, Object> properties, final int pageSize, final int pageNum) {
		final TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, properties);
		paginar(pageSize, pageNum, typedQuery);
		return typedQuery.getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#getByQuery(java.lang.String,
	 * java.util.Map, int, int)
	 */
	@Override
	public List<?> getByQuery(final String nombreQuery, final Map<String, Object> parameters, final int pageSize,
			final int pageNum) {
		this.logger.log(Level.FINE, "Lanzando la getByQuery {0}", nombreQuery);
		final Query query = this.em.createNamedQuery(nombreQuery);
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
	public Object getByQuerySingleResult(final String nombreQuery, final Map<String, Object> parameters) {
		this.logger.log(Level.FINE, "Lanzando la la getByQuerySingleResult {0} ", nombreQuery);
		final Query query = this.em.createNamedQuery(nombreQuery);
		parametrizar(parameters, query);
		Object result = null;
		try {
			result = query.getSingleResult();
		} catch (final NoResultException ex) {
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
	public <T extends AbstractEntity> List<T> getByTypedQuery(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final int pageSize, final int pageNum) {
		this.logger.log(Level.FINE, "Ejecutando la getByTypedQuery: {0}", namedQuery);
		final TypedQuery<T> query = this.em.createNamedQuery(namedQuery, entityClass);
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
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		this.logger.log(Level.FINE, "Ejecutando la getByTypedQuerySingleResult: {0}", namedQuery);
		final TypedQuery<T> query = this.em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		final T result = query.getSingleResult();
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#getCriteriaBuilder()
	 */
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return this.em.getCriteriaBuilder();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#getReference(java.lang.Class,
	 * java.lang.Object)
	 */
	@Override
	public <T extends AbstractEntity> T getReference(final Class<T> entityClass, final Object primaryKey) {
		this.logger.log(Level.FINE, "Reference de la entidad::Clase={0}::Id={1}",
				new Object[] { entityClass, primaryKey });
		return this.em.getReference(entityClass, primaryKey);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#persist(T)
	 */
	@Override
	public <T extends AbstractEntity> void insert(final T entity) {
		this.logger.log(Level.FINE, "Insertando una nueva entidad de tipod::Clase={0}",
				entity.getClass().getCanonicalName());
		this.em.persist(entity);
		this.logger.log(Level.FINE, "Se le ha asignado la id={0}", entity.getId());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#lock(T,
	 * javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public <T extends AbstractEntity> void lock(final T entity, final LockModeType lockModeType,
			final Map<String, Object> properties) {
		this.logger.log(Level.FINE, "Bloqueando la entidad::clase={0}::id={1}::lockMode={2}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId(), lockModeType });
		this.em.lock(entity, lockModeType, properties);
	}

	private void paginar(final int pageSize, final int pageNum, final Query query) {
		if (pageNum < 0) {
			throw new IllegalArgumentException("pageNum: " + pageNum);
		}

		if (pageSize < 0) {
			throw new IllegalArgumentException("pageSize: " + pageSize);
		}

		this.logger.log(Level.FINEST, "Página actual {0}", pageNum);
		query.setFirstResult(pageNum * pageSize);

		if (pageSize > 0) {
			this.logger.log(Level.FINEST, "Tamaño de página {0}", pageSize);
			query.setMaxResults(pageSize);
		} else if (pageSize == 0) {
			this.logger.log(Level.FINEST, "Limitando a {0} resultados", RES_LIMIT);
			query.setMaxResults(RES_LIMIT);
		}
	}

	private void parametrizar(final Map<String, Object> parameters, final Query query) {
		if (parameters != null) {
			for (final String key : parameters.keySet()) {
				final Object parameter = parameters.get(key);
				this.logger.log(Level.FINEST, "key={0}::parameter={1}", new Object[] { key, parameter });
				query.setParameter(key, parameter);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#refresh(T,
	 * javax.persistence.LockModeType, java.util.Map)
	 */
	@Override
	public <T extends AbstractEntity> void refresh(final T entity, final LockModeType lockModeType,
			final Map<String, Object> properties) {
		this.logger.log(Level.FINE, "Refresh de la entidad::clase={0}::id={1}::lockMode={2}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId(), lockModeType });
		this.em.refresh(entity, lockModeType, properties);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * afersa.core.model.PersistenceFacade#setEm(javax.persistence.EntityManager
	 * )
	 */
	@Override
	public void setEm(final EntityManager em) {
		this.em = em;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * afersa.core.model.PersistenceFacade#setLogger(java.util.logging.Logger)
	 */
	@Override
	public void setLogger(final Logger logger) {
		this.logger = logger;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see afersa.core.model.PersistenceFacade#update(T)
	 */
	@Override
	public <T extends AbstractEntity> T update(final T entity) {
		this.logger.log(Level.FINE, "Actualizando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		if (!this.em.contains(entity)) {
			return this.em.merge(entity);
		} else {
			return entity;
		}
	}
}
