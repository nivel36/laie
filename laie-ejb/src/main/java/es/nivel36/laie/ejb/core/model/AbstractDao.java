package es.nivel36.laie.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class AbstractDao {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

	private static final String CACHE_STORE_MODE = "javax.persistence.cache.storeMode";

	protected @PersistenceContext(unitName = "laie") EntityManager em;

	public <E extends Identifiable> void insert(E entity) {
		Objects.requireNonNull(entity);
		this.em.persist(entity);
	}

	public <E extends Identifiable> E update(E entity) {
		Objects.requireNonNull(entity);
		return this.em.merge(entity);
	}

	public <T extends Identifiable> void delete(final Class<T> type, final T entity) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(type);
		if (entity.getId() == 0) {
			throw new IllegalStateException();
		}
		logger.debug("Delete entity class {} with id {}", type, entity.getId());
		if (this.em.contains(entity)) {
			this.em.remove(entity);
		} else {
			this.em.remove(this.em.merge(entity));
		}
	}

	protected <E extends Identifiable> boolean checkDuplicateField(final Class<E> type, final String fieldName,
			final Object fieldValue) {
		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb.createQuery(type);
		final Root<E> root = cq.from(type);
		cq.select(root).where(cb.equal(root.get(fieldName), fieldValue));
		cq.select(root.get(fieldName));
		final List<E> elements = this.findByCriteria(cq, new Page(0, 1));
		return elements.size() > 0;
	}

	public <E> E find(final Class<E> type, final Long id) {
		Objects.requireNonNull(type);
		Objects.requireNonNull(id);
		logger.debug("Find entity of class {} with id", type, id);
		return em.find(type, id);
	}

	public <E> List<E> findAll(final Class<E> type, final Page page) {
		Objects.requireNonNull(type);
		logger.debug("Find all entities of class {}", type);
		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb.createQuery(type);
		final Root<E> root = cq.from(type);
		final CriteriaQuery<E> all = cq.select(root);
		return this.findByCriteria(all, page);
	}

	protected <E> List<E> findByCriteria(final CriteriaQuery<E> cq, final Page page) {
		Objects.requireNonNull(cq);
		Objects.requireNonNull(page);
		logger.debug("Find entities by criteria");
		final TypedQuery<E> query = this.em.createQuery(cq);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.paginate(page, query);
		return query.getResultList();
	}

	protected <E> E findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		return this.findByQuery(entityClass, namedQuery, parameters, FlushModeType.AUTO);
	}

	protected <E> E findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final FlushModeType flusModeType) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entity {} by named query {}", entityClass, namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		query.setFlushMode(flusModeType);
		this.parametrize(parameters, query);
		return query.getSingleResult();
	}

	protected <E> List<E> findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final Page page) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		Objects.requireNonNull(page);
		logger.debug("Find entities {} by named query {}", entityClass, namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.parametrize(parameters, query);
		this.paginate(page, query);
		return query.getResultList();
	}

	protected Object findByQuery(final String namedQuery, final Map<String, Object> parameters) {
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entity by named query {}", namedQuery);
		final Query query = this.em.createNamedQuery(namedQuery);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.parametrize(parameters, query);
		return query.getSingleResult();
	}

	private void paginate(final Page page, final Query query) {
		query.setFirstResult(page.getOffset());
		query.setMaxResults(page.getLimit());
	}

	private void parametrize(final Map<String, Object> parameters, final Query query) {
		if (parameters == null) {
			return;
		}
		for (final Map.Entry<String, Object> entry : parameters.entrySet()) {
			logger.trace("Paramtrize query with key {} value={}", entry.getKey(), entry.getValue());
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}