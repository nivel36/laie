package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

@Repository
public class PersistenceFacadeJpa implements PersistenceFacade {

	/**
	 * El número máximo de resultados que permiten las búsquedas
	 */
	private final static int RES_LIMIT = 150;

	@Inject
	private EntityManager em;

	@Inject
	private Logger logger;

	private <K, T extends Entity<K>> TypedQuery<T> createQueryFromCriteria(final Class<T> clazz,
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

	@Override
	public <K, T extends Entity<K>> void delete(final T entity) {
		this.logger.log(Level.FINE, "Eliminando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		if (this.em.contains(entity)) {
			this.em.remove(entity);
		} else {
			this.em.remove(this.em.merge(entity));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <K, T extends Entity<K>> List<T> fullSearch(final Class<T> clazz, final List<String> fields,
			final List<String> matching) {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.em);
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(clazz).get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		for (int i = 0; i < fields.size(); i++) {
			final String value = matching.get(i);
			if ((value != null) && (value.length() != 0)) {
				bj.must(qb.keyword().onField(fields.get(i)).matching(value).createQuery());
			}
		}
		final Query persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), clazz);
		final List<T> result = persistenceQuery.getResultList();
		return result;
	}

	@Override
	public <K, T extends Entity<K>> List<T> getAll(final Class<T> clazz) {
		final TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, null);
		paginar(0, RES_LIMIT, typedQuery);
		return typedQuery.getResultList();
	}

	@Override
	public <T> List<T> getByCriteria(final CriteriaQuery<T> cq, final int pageSize, final int pageNum) {
		this.logger.log(Level.FINE, "Lanzando criteria");
		final TypedQuery<T> query = this.em.createQuery(cq);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	@Override
	public <T> T getByCriteriaSingleResult(final CriteriaQuery<T> cq) {
		this.logger.log(Level.FINE, "Lanzando criteria para un solo resultado");
		final TypedQuery<T> query = this.em.createQuery(cq);
		return query.getSingleResult();
	}

	@Override
	public <K, T extends Entity<K>> T getByPrimaryKey(final Class<T> entityClass, final K id) {
		this.logger.log(Level.FINE, "Buscando por clave primaria::clase={0}::id={1}", new Object[] { entityClass, id });
		return this.em.find(entityClass, id);
	}

	@Override
	public <K, T extends Entity<K>> T getByProperties(final Class<T> clazz, final Map<String, Object> properties) {
		final TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, properties);
		parametrizar(properties, typedQuery);
		return typedQuery.getSingleResult();
	}

	@Override
	public <K, T extends Entity<K>> List<T> getByProperties(final Class<T> clazz, final Map<String, Object> properties,
			final int pageSize, final int pageNum) {
		final TypedQuery<T> typedQuery = createQueryFromCriteria(clazz, properties);
		paginar(pageSize, pageNum, typedQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<?> getByQuery(final String nombreQuery, final Map<String, Object> parameters, final int pageSize,
			final int pageNum) {
		this.logger.log(Level.FINE, "Lanzando la getByQuery {0}", nombreQuery);
		final Query query = this.em.createNamedQuery(nombreQuery);
		paginar(pageSize, pageNum, query);
		parametrizar(parameters, query);
		return query.getResultList();
	}

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

	@Override
	public <K, T extends Entity<K>> List<T> getByTypedQuery(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final int pageSize, final int pageNum) {
		this.logger.log(Level.FINE, "Ejecutando la getByTypedQuery: {0}", namedQuery);
		final TypedQuery<T> query = this.em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	@Override
	public <K, T extends Entity<K>> T getByTypedQuerySingleResult(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		this.logger.log(Level.FINE, "Ejecutando la getByTypedQuerySingleResult: {0}", namedQuery);
		final TypedQuery<T> query = this.em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		final T result = query.getSingleResult();
		return result;
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return this.em.getCriteriaBuilder();
	}

	@Override
	public EntityManager getEm() {
		return this.em;
	}

	@Override
	public <K, T extends Entity<K>> void insert(final T entity) {
		this.logger.log(Level.FINE, "Insertando una nueva entidad de tipod::Clase={0}",
				entity.getClass().getCanonicalName());
		this.em.persist(entity);
		this.logger.log(Level.FINE, "Se le ha asignado la id={0}", entity.getId());
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

	@Override
	public <K, T extends Entity<K>> T update(final T entity) {
		this.logger.log(Level.FINE, "Actualizando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		if (!this.em.contains(entity)) {
			return this.em.merge(entity);
		} else {
			return entity;
		}
	}
}
