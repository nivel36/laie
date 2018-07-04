package ged.ejb.core.model;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class PersistenceFacadeJpa implements PersistenceFacade {

	private static final String CACHE_STORE_MODE = "javax.persistence.cache.storeMode";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	// max number of results
	private static final int RES_LIMIT = 150;

	private final EntityManager em;

	@Inject
	public PersistenceFacadeJpa(final EntityManager em) {
		this.em = em;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.PersistenceFacade#delete(java.lang.Class, T)
	 */
	@Override
	public <T extends Identificable> void delete(final Class<T> type, final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			throw new IllegalStateException();
		}
		logger.debug("Delete entity class {} with id {}", type, entity.getId());
		if (this.em.contains(entity)) {
			this.em.remove(entity);
		}
		else {
			final T attachedEntity = this.em.getReference(type, entity.getId());
			this.em.remove(attachedEntity);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.PersistenceFacade#find(java.lang.Class, long)
	 */
	@Override
	public <T extends Identificable> T find(final Class<T> type, final long id) {
		Objects.requireNonNull(id);
		logger.debug("Find class {} by id {}", type, id);
		return this.em.find(type, id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.PersistenceFacade#findAll(java.lang.Class)
	 */
	@Override
	public <E> List<E> findAll(final Class<E> type) {
		Objects.requireNonNull(type);
		logger.debug("Find all entities of class {}", type);
		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb.createQuery(type);
		final Root<E> root = cq.from(type);
		final CriteriaQuery<E> all = cq.select(root);
		return this.findByCriteria(all, null, null);
	}

	private <E> List<E> findByCriteria(final CriteriaQuery<E> cq, final Integer pageSize, final Integer pageNum) {
		Objects.requireNonNull(cq);
		logger.debug("Find entities by criteria");
		final TypedQuery<E> query = this.em.createQuery(cq);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.paginate(pageSize, pageNum, query);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.PersistenceFacade#findByTypedQuery(java.lang.Class,
	 * java.lang.String, java.util.Map)
	 */
	@Override
	public <E> E findByQuery(final Class<E> entityClass, final String namedQuery, final Map<String, Object> parameters) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entity {} by named query {}", entityClass, namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.parametrize(parameters, query);
		return query.getSingleResult();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.PersistenceFacade#findByTypedQuery(java.lang.Class,
	 * java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public <E> List<E> findByQuery(final Class<E> entityClass, final String namedQuery, final Map<String, Object> parameters, final Integer pageSize,
			final Integer pageNum) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entities {} by named query {}", entityClass, namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.parametrize(parameters, query);
		this.paginate(pageSize, pageNum, query);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.PersistenceFacade#findByQuery(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public Object findByQuery(final String namedQuery, final Map<String, Object> parameters) {
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entity by named query {}", namedQuery);
		final Query query = this.em.createNamedQuery(namedQuery);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.parametrize(parameters, query);
		return query.getSingleResult();
	}

	public EntityManager getEm() {
		return this.em;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.PersistenceFacade#insert(T)
	 */
	@Override
	public <T extends Identificable> void insert(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() != 0) {
			throw new IllegalStateException();
		}
		logger.debug("Insert entity of class {}", entity.getClass());
		this.em.persist(entity);
		logger.debug("Innserted entity has the id {}", entity.getId());
	}

	private void paginate(final Integer pageSize, final Integer pageNum, final Query query) {
		if ((pageNum != null) && (pageNum < 0)) {
			throw new IllegalArgumentException("pageNum: " + pageNum);
		}
		if ((pageSize != null) && (pageSize < 0)) {
			throw new IllegalArgumentException("pageSize: " + pageSize);
		}
		logger.trace("Page number {}", pageNum);
		if ((pageSize != null) && (pageNum != null)) {
			query.setFirstResult(pageNum * pageSize);
		}
		if ((pageSize != null) && (pageSize > 0)) {
			logger.trace("Page size {}", pageSize);
			query.setMaxResults(pageSize);
		}
		else if ((pageSize == null) || (pageSize == 0)) {
			logger.trace("Setting max result to {}", RES_LIMIT);
			query.setMaxResults(RES_LIMIT);
		}
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Identificable> List<T> search(final Class<T> type, final String searchText, final String... fields) {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(type).get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();

		if (searchText != null) {
			final List<String> searchValues = Arrays.asList(searchText.split("\\s"));
			for (final String searchValue : searchValues) {
				if (searchValue == null) {
					continue;
				}
				final BooleanJunction<BooleanJunction> fieldBj = qb.bool();
				fieldBj.should(qb.keyword().onFields(fields).matching(searchValue).createQuery());
				bj.must(fieldBj.createQuery());
			}
		}

		final Query persistenceQuery;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), type);
		}
		else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), type);
		}
		persistenceQuery.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		return persistenceQuery.getResultList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ged.ejb.core.model.PersistenceFacade#update(T)
	 */
	@Override
	public <T extends Identificable> T update(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			throw new IllegalStateException();
		}
		logger.debug("Update entity of class {} and id {}", entity.getClass(), entity.getId());
		if (this.em.contains(entity)) {
			return entity;
		}
		else {
			return this.em.merge(entity);
		}
	}
}