package ged.ejb.core.model;

import java.lang.invoke.MethodHandles;
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
public abstract class AbstractDaoJpa<T extends AbstractEntity> implements Dao<T> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	// max number of results
	private static final int RES_LIMIT = 150;

	private final EntityManager em;

	@Inject
	public AbstractDaoJpa(final EntityManager em) {
		this.em = em;
	}

	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			throw new IllegalStateException();
		}
		logger.debug("Delete entity class {} with id {}", entity.getClass(), entity.getId());
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
		logger.debug("Find class {} by id {}", getType(), id);
		return this.em.find(getType(), id);
	}

	@Override
	public List<T> findAll() {
		logger.debug("Find all entities of class {}", getType());
		return findAll(getType());
	}

	protected <E> List<E> findAll(final Class<E> type) {
		Objects.requireNonNull(type);
		logger.debug("Find all entities of class {}", type);
		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb.createQuery(type);
		final Root<E> root = cq.from(type);
		final CriteriaQuery<E> all = cq.select(root);
		return findByCriteria(all, 0, 0);
	}

	protected <E> List<E> findByCriteria(final CriteriaQuery<E> cq, final int pageSize, final int pageNum) {
		Objects.requireNonNull(cq);
		logger.debug("Find entities by criteria");
		final TypedQuery<E> query = this.em.createQuery(cq);
		paginar(pageSize, pageNum, query);
		return query.getResultList();
	}

	protected T findByCriteria(final CriteriaQuery<T> cq) {
		Objects.requireNonNull(cq);
		logger.debug("Find entity by criteria");
		final TypedQuery<T> query = this.em.createQuery(cq);
		return query.getSingleResult();
	}

	protected Object findByQuery(final String namedQuery) {
		return findByQuery(namedQuery, null);
	}

	protected Object findByQuery(final String namedQuery, final Map<String, Object> parameters) {
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entity by named query {}", namedQuery);
		final Query query = this.em.createNamedQuery(namedQuery);
		parametrizar(parameters, query);
		return query.getSingleResult();
	}

	protected <E> E findByTypedQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entity {} by named query {}", entityClass, namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		parametrizar(parameters, query);
		return query.getSingleResult();
	}

	protected <E> List<E> findByTypedQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final int pageSize, final int pageNum) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entities {} by named query {}", entityClass, namedQuery);
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
		logger.debug("Insert entity of class {}", getType());
		this.em.persist(entity);
		logger.debug("Innsertedd entity has the id {}", entity.getId());
	}

	private void paginar(final int pageSize, final int pageNum, final Query query) {
		if (pageNum < 0) {
			throw new IllegalArgumentException("pageNum: " + pageNum);
		}
		if (pageSize < 0) {
			throw new IllegalArgumentException("pageSize: " + pageSize);
		}
		logger.trace("Page number {}", pageNum);
		query.setFirstResult(pageNum * pageSize);
		if (pageSize > 0) {
			logger.trace("Page size {}", pageSize);
			query.setMaxResults(pageSize);
		} else if (pageSize == 0) {
			logger.warn("Setting max result to {}", RES_LIMIT);
			query.setMaxResults(RES_LIMIT);
		}
	}

	private void parametrizar(final Map<String, Object> parameters, final Query query) {
		if (parameters == null) {
			return;
		}
		for (final Map.Entry<String, Object> entry : parameters.entrySet()) {
			logger.trace("Paramtrize query with key {} value={}", entry.getKey(), entry.getValue());
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public T update(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			throw new IllegalStateException();
		}
		logger.debug("Update entity of class {} and id {}", new Object[] { getType(), entity.getId() });
		if (this.em.contains(entity)) {
			return entity;
		} else {
			return this.em.merge(entity);
		}
	}
}