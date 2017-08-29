package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public abstract class AbstractDao<T extends Identificable> implements Dao<T> {

	private final static Logger logger = LoggerFactory.getLogger(AbstractDao.class.getName());

	// max number of results
	private static final int RES_LIMIT = 150;

	private final EntityManager em;

	@Inject
	public AbstractDao(final EntityManager em) {
		this.em = em;
	}

	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			throw new IllegalStateException();
		}
		logger.debug("Eliminando la entidad::Clase={}::Id={}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		if (this.em.contains(entity)) {
			this.em.remove(entity);
		} else {
			final T attachedEntity = this.em.getReference(getType(), entity.getId());
			this.em.remove(attachedEntity);
		}
	}

	@Override
	public T find(final long id) {
		Objects.requireNonNull(id);
		logger.debug("Buscando por clave primaria::clase={}::id={}", new Object[] { getClass(), id });
		return this.em.find(getType(), id);
	}

	@Override
	public List<T> findAll() {
		return findAll(getType());
	}

	protected <E> List<E> findAll(final Class<E> type) {
		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb.createQuery(type);
		final Root<E> root = cq.from(type);
		final CriteriaQuery<E> all = cq.select(root);
		return findByCriteria(all, 0, 0);
	}

	protected <E> List<E> findByCriteria(final CriteriaQuery<E> cq, final int pageSize, final int pageNum) {
		Objects.requireNonNull(cq);
		logger.debug("Lanzando criteria");
		final TypedQuery<E> query = this.em.createQuery(cq);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	protected T findByCriteria(final CriteriaQuery<T> cq) {
		Objects.requireNonNull(cq);
		logger.debug("Lanzando criteria para un solo resultado");
		final TypedQuery<T> query = this.em.createQuery(cq);
		return query.getSingleResult();
	}

	protected Object findByQuery(final String namedQuery) {
		return findByQuery(namedQuery, null);
	}

	protected Object findByQuery(final String namedQuery, final Map<String, Object> parameters) {
		Objects.requireNonNull(namedQuery);
		logger.debug("Ejecutando la findByQuery: {}", namedQuery);
		final Query query = this.em.createNamedQuery(namedQuery);
		parametrizar(parameters, query);
		return query.getSingleResult();
	}

	protected <E> E findByTypedQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.debug("Ejecutando la getByTypedQuerySingleResult: {}", namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		return query.getSingleResult();
	}

	protected <E> List<E> findByTypedQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final int pageSize, final int pageNum) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.debug("Ejecutando la getByTypedQuery: {}", namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	protected <E> List<E> findByTypedQuery(final Class<E> entityClass, final String namedQuery, final int pageSize,
			final int pageNum) {
		return findByTypedQuery(entityClass, namedQuery, null, pageSize, pageNum);
	}

	protected EntityManager getEm() {
		return this.em;
	}

	protected abstract Class<T> getType();

	@Override
	public void insert(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() != 0) {
			throw new IllegalStateException();
		}
		logger.debug("Insertando una nueva entidad de tipod::Clase={}", entity.getClass().getCanonicalName());
		this.em.persist(entity);
		logger.debug("Se le ha asignado la id={}", entity.getId());
	}

	private void paginar(final int pageSize, final int pageNum, final Query query) {
		if (pageNum < 0) {
			throw new IllegalArgumentException("pageNum: " + pageNum);
		}
		if (pageSize < 0) {
			throw new IllegalArgumentException("pageSize: " + pageSize);
		}
		logger.debug("Página actual {}", pageNum);
		query.setFirstResult(pageNum * pageSize);
		if (pageSize > 0) {
			logger.debug("Tamaño de página {}", pageSize);
			query.setMaxResults(pageSize);
		} else if (pageSize == 0) {
			logger.debug("Limitando a {} resultados", RES_LIMIT);
			query.setMaxResults(RES_LIMIT);
		}
	}

	private void parametrizar(final Map<String, Object> parameters, final Query query) {
		if (parameters == null) {
			return;
		}
		for (final Map.Entry<String, Object> entry : parameters.entrySet()) {
			logger.debug("key={}::parameter={}", new Object[] { entry.getKey(), entry.getValue() });
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public T update(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			throw new IllegalStateException();
		}
		logger.debug("Actualizando la entidad::Clase={}::Id={}",
				new Object[] { entity.getClass().getCanonicalName(), entity.getId() });
		if (this.em.contains(entity)) {
			return entity;
		} else {
			return this.em.merge(entity);
		}
	}
}