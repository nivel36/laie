package ged.ejb.core.model;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
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

import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortFieldContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.SearchFilter.SearchCondition;

@Repository
public class PersistenceFacade {

	private static final String CACHE_STORE_MODE = "javax.persistence.cache.storeMode";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final EntityManager em;

	@Inject
	public PersistenceFacade(final EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("rawtypes")
	private org.apache.lucene.search.Query createLuceneQuery(final QueryBuilder qb,
			final BooleanJunction<BooleanJunction> bj) {
		org.apache.lucene.search.Query luceneQuery;
		if (bj.isEmpty()) {
			luceneQuery = qb.all().createQuery();
		} else {
			luceneQuery = bj.createQuery();
		}
		return luceneQuery;
	}

	@SuppressWarnings("rawtypes")
	private BooleanJunction<BooleanJunction> createPredicate(final String searchText, final QueryBuilder qb,
			final String... fields) {
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
		return bj;
	}

	@SuppressWarnings("rawtypes")
	private BooleanJunction<BooleanJunction> createPredicateFilters(final QueryBuilder qb,
			final SearchFilters searchFilters) {
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		for (final SearchFilter searchFilter : searchFilters) {
			final org.apache.lucene.search.Query createQuery = qb.keyword().onField(searchFilter.getName())
					.matching(searchFilter.getValue()).createQuery();
			if (searchFilter.getSearchCondition().equals(SearchCondition.AND)) {
				bj.should(createQuery);
			} else {
				bj.must(createQuery);
			}
		}
		return bj;
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

	public <T extends Identifiable> T find(final Class<T> type, final long id) {
		Objects.requireNonNull(id);
		logger.debug("Find class {} by id {}", type, id);
		return this.em.find(type, id);
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

	private <E> List<E> findByCriteria(final CriteriaQuery<E> cq, final Page page) {
		Objects.requireNonNull(cq);
		Objects.requireNonNull(page);
		logger.debug("Find entities by criteria");
		final TypedQuery<E> query = this.em.createQuery(cq);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.paginate(page, query);
		return query.getResultList();
	}

	public <E> E findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		logger.debug("Find entity {} by named query {}", entityClass, namedQuery);
		final TypedQuery<E> query = this.em.createNamedQuery(namedQuery, entityClass);
		query.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);
		this.parametrize(parameters, query);
		return query.getSingleResult();
	}

	public <E> List<E> findByQuery(final Class<E> entityClass, final String namedQuery,
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

	private boolean hasSortFields(final List<SortField> sortFields) {
		return sortFields != null && !sortFields.isEmpty();
	}

	public <T extends Identifiable> void insert(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() != 0) {
			throw new IllegalStateException();
		}
		logger.debug("Insert entity of class {}", entity.getClass());
		this.em.persist(entity);
		logger.debug("Innserted entity has the id {}", entity.getId());
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Identifiable> SearchResult<T> search(final Class<T> type, final Page page,
			final List<SortField> sortFields, final SearchFilters searchFilter, final String searchText,
			final String... fields) {

		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(type).get();

		final BooleanJunction<BooleanJunction> bj = this.createPredicateFilters(qb, searchFilter);
		bj.must(this.createPredicate(searchText, qb, fields).createQuery());

		final FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(this.createLuceneQuery(qb, bj),
				type);
		this.paginate(page, fullTextQuery);
		fullTextQuery.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);

		this.sortQuery(sortFields, qb, fullTextQuery);

		final List<T> results = fullTextQuery.getResultList();
		if (results instanceof ArrayList) {
			return new SearchResult<>(results, fullTextQuery.getResultSize());
		} else {
			return new SearchResult<>(new ArrayList<>(results), fullTextQuery.getResultSize());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Identifiable> SearchResult<T> search(final Class<T> type, final Page page,
			final List<SortField> sortFields, final String searchText, final String... fields) {

		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(type).get();

		final BooleanJunction<BooleanJunction> bj = this.createPredicate(searchText, qb, fields);

		final FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(this.createLuceneQuery(qb, bj),
				type);
		this.paginate(page, fullTextQuery);
		fullTextQuery.setHint(CACHE_STORE_MODE, CacheStoreMode.REFRESH);

		this.sortQuery(sortFields, qb, fullTextQuery);

		final List<T> results = fullTextQuery.getResultList();
		if (results instanceof ArrayList) {
			return new SearchResult<>(results, fullTextQuery.getResultSize());
		} else {
			return new SearchResult<>(new ArrayList<>(results), fullTextQuery.getResultSize());
		}
	}

	private void sortQuery(final List<SortField> sortFields, final QueryBuilder qb, final FullTextQuery fullTextQuery) {
		if (this.hasSortFields(sortFields)) {
			final int orderSize = sortFields.size();
			final SortFieldContext sfc = qb.sort().byField(sortFields.get(0).getField());
			if (sortFields.get(0).isDescending()) {
				sfc.asc();
			} else {
				sfc.desc();
			}
			for (int i = 1; i < orderSize; i++) {
				sfc.andByField(sortFields.get(i).getField());
				if (sortFields.get(0).isDescending()) {
					sfc.asc();
				} else {
					sfc.desc();
				}
			}
			final Sort sort = sfc.createSort();
			fullTextQuery.setSort(sort);
		}
	}

	public <T extends Identifiable> T update(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			throw new IllegalStateException();
		}
		logger.debug("Update entity of class {} and id {}", entity.getClass(), entity.getId());
		if (this.em.contains(entity)) {
			return entity;
		} else {
			return this.em.merge(entity);
		}
	}
}