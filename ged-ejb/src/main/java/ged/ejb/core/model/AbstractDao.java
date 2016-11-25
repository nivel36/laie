package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Repository
public abstract class AbstractDao<K, T extends Entity<K>> implements Dao<K, T> {

	private final static Logger logger = Logger.getLogger(AbstractDao.class.getName());

	/**
	 * El número máximo de resultados que permiten las búsquedas
	 */
	private static final int RES_LIMIT = 150;

	private final EntityManager em;

	@Inject
	public AbstractDao(final EntityManager em) {
		this.em = em;
	}

	protected <E> TypedQuery<E> createQueryFromCriteria(final Class<E> clazz) {
		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb.createQuery(clazz);
		return this.em.createQuery(cq);
	}

	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == null) {
			throw new IllegalStateException();
		}
		logger.log(Level.FINE, "Eliminando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		if (this.em.contains(entity)) {
			this.em.remove(entity);
		} else {
			final T attachedEntity = this.em.getReference(getType(), entity.getId());
			this.em.remove(attachedEntity);
		}
	}

	@Override
	public T find(final K id) {
		Objects.requireNonNull(id);
		logger.log(Level.FINE, "Buscando por clave primaria::clase={0}::id={1}", new Object[] { getClass(), id });
		return this.em.find(getType(), id);
	}

	@Override
	public List<T> findAll() {
		final TypedQuery<T> typedQuery = createQueryFromCriteria(getType());
		paginar(0, RES_LIMIT, typedQuery);
		return typedQuery.getResultList();
	}

	protected T findByCriteria(final CriteriaQuery<T> cq) {
		Objects.requireNonNull(cq);
		logger.log(Level.FINE, "Lanzando criteria para un solo resultado");
		final TypedQuery<T> query = this.em.createQuery(cq);
		return query.getSingleResult();
	}

	protected List<T> findByCriteria(final CriteriaQuery<T> cq, final int pageSize, final int pageNum) {
		Objects.requireNonNull(cq);
		logger.log(Level.FINE, "Lanzando criteria");
		final TypedQuery<T> query = this.em.createQuery(cq);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	protected <E> E findByTypedQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.log(Level.FINE, "Ejecutando la getByTypedQuerySingleResult: {0}", namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		return query.getSingleResult();
	}

	protected <E> List<E> findByTypedQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final int pageSize, final int pageNum) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.log(Level.FINE, "Ejecutando la getByTypedQuery: {0}", namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	protected EntityManager getEm() {
		return this.em;
	}

	protected abstract Class<T> getType();

	@Override
	public void insert(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() != null) {
			throw new IllegalStateException();
		}
		logger.log(Level.FINE, "Insertando una nueva entidad de tipod::Clase={0}",
				entity.getClass().getCanonicalName());
		this.em.persist(entity);
		logger.log(Level.FINE, "Se le ha asignado la id={0}", entity.getId());
	}

	private void paginar(final int pageSize, final int pageNum, final Query query) {
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

	private void parametrizar(final Map<String, Object> parameters, final Query query) {
		if (parameters == null) {
			return;
		}
		for (final Map.Entry<String, Object> entry : parameters.entrySet()) {
			logger.log(Level.FINEST, "key={0}::parameter={1}", new Object[] { entry.getKey(), entry.getValue() });
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public T update(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == null) {
			throw new IllegalStateException();
		}
		logger.log(Level.FINE, "Actualizando la entidad::Clase={0}::Id={1}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		if (this.em.contains(entity)) {
			return entity;
		} else {
			return this.em.merge(entity);
		}
	}
}