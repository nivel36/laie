package es.nivel36.laie.ejb.core.model;

import java.util.List;
import java.util.Objects;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Base class for Data Access Objects (DAO) providing generic CRUD operations
 * and queries using the Criteria API.
 * <p>
 * Subclasses should define the persistence unit via {@code @PersistenceContext}
 * and may optionally inject a {@link SearchFacade} for advanced search
 * capabilities.
 * </p>
 */
public abstract class AbstractDao {

	/**
	 * EntityManager for interacting with the persistence context.
	 */
	protected @PersistenceContext(unitName = "laie") EntityManager em;

	/**
	 * Optional component for advanced search operations.
	 */
	protected @Inject SearchFacade searchFacade;

	/**
	 * Persists the given entity in the database.
	 *
	 * @param <E>    the type of the entity implementing {@link Identifiable}
	 * @param entity the entity instance to persist; must not be {@code null}
	 * @throws NullPointerException if {@code entity} is {@code null}
	 */
	public <E extends Identifiable> void insert(final E entity) {
		Objects.requireNonNull(entity, "Entity must not be null");
		this.em.persist(entity);
	}

	/**
	 * Merges the state of the given entity into the current persistence context.
	 *
	 * @param <E>    the type of the entity implementing {@link Identifiable}
	 * @param entity the entity instance with changes; must not be {@code null}
	 * @return the managed instance of the entity
	 * @throws NullPointerException if {@code entity} is {@code null}
	 */
	public <E extends Identifiable> E update(final E entity) {
		Objects.requireNonNull(entity, "Entity must not be null");
		return this.em.merge(entity);
	}

	/**
	 * Removes the given entity from the database.
	 *
	 * @param <T>    the type of the entity implementing {@link Identifiable}
	 * @param type   the class of the entity; must not be {@code null}
	 * @param entity the entity instance to remove; must not be {@code null}
	 * @throws NullPointerException  if {@code type} or {@code entity} is
	 *                               {@code null}
	 * @throws IllegalStateException if the entity's identifier is less than or
	 *                               equal to zero
	 */
	public <T extends Identifiable> void delete(final Class<T> type, final T entity) {
		Objects.requireNonNull(type, "Entity type must not be null");
		Objects.requireNonNull(entity, "Entity must not be null");
		if (entity.getId() <= 0) {
			throw new IllegalStateException("Entity identifier is not valid");
		}
		if (this.em.contains(entity)) {
			this.em.remove(entity);
		} else {
			this.em.remove(this.em.merge(entity));
		}
	}

	/**
	 * Checks whether at least one entity of the given type exists with the
	 * specified field value.
	 *
	 * @param <E>        the type of the entity implementing {@link Identifiable}
	 * @param type       the class of the entity; must not be {@code null}
	 * @param fieldName  the field name to check; must not be {@code null}
	 * @param fieldValue the value to compare; may be {@code null}
	 * @return {@code true} if an entity with the given field value exists;
	 *         {@code false} otherwise
	 * @throws NullPointerException if {@code type} or {@code fieldName} is
	 *                              {@code null}
	 */
	protected <E extends Identifiable> boolean fieldExists(final Class<E> type, final String fieldName,
			final Object fieldValue) {
		Objects.requireNonNull(type, "Entity type must not be null");
		Objects.requireNonNull(fieldName, "Field name must not be null");

		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<E> root = cq.from(type);

		cq.select(cb.count(root)).where(cb.equal(root.get(fieldName), fieldValue));

		final Long count = em.createQuery(cq).getSingleResult();
		return count != null && count > 0;
	}

	/**
	 * Finds an entity by its primary key.
	 *
	 * @param <E>  the type of the entity
	 * @param type the class of the entity; must not be {@code null}
	 * @param id   the primary key of the entity
	 * @return the found entity instance or {@code null} if not found
	 * @throws NullPointerException if {@code type} is {@code null}
	 */
	public <E> E find(final Class<E> type, final long id) {
		Objects.requireNonNull(type, "Entity type must not be null");
		return em.find(type, id);
	}

	/**
	 * Retrieves all entities of the given type with pagination.
	 *
	 * @param <E>  the type of the entity
	 * @param type the class of the entity; must not be {@code null}
	 * @param page the pagination parameters; must not be {@code null}
	 * @return a list of entities for the requested page
	 * @throws NullPointerException if {@code type} or {@code page} is {@code null}
	 */
	public <E> List<E> findAll(final Class<E> type, final Page page) {
		Objects.requireNonNull(type, "Entity type must not be null");
		Objects.requireNonNull(page, "Page must not be null");

		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb.createQuery(type);
		final Root<E> root = cq.from(type);
		final CriteriaQuery<E> selectAll = cq.select(root);

		return this.findByCriteria(selectAll, page);
	}

	/**
	 * Executes a CriteriaQuery with pagination.
	 *
	 * @param <E>  the result type of the query
	 * @param cq   the {@link CriteriaQuery} to execute; must not be {@code null}
	 * @param page the pagination parameters; must not be {@code null}
	 * @return a list of query results
	 * @throws NullPointerException if {@code cq} or {@code page} is {@code null}
	 */
	protected <E> List<E> findByCriteria(final CriteriaQuery<E> cq, final Page page) {
		Objects.requireNonNull(cq, "CriteriaQuery must not be null");
		Objects.requireNonNull(page, "Page must not be null");

		final TypedQuery<E> query = this.em.createQuery(cq);
		this.paginate(page, query);
		return query.getResultList();
	}

	/**
	 * Applies pagination parameters to a JPA query.
	 *
	 * @param page  the pagination parameters; must not be {@code null}
	 * @param query the {@link Query} or {@link TypedQuery}; must not be
	 *              {@code null}
	 * @throws NullPointerException if {@code page} or {@code query} is {@code null}
	 */
	protected void paginate(final Page page, final Query query) {
		Objects.requireNonNull(page, "Page must not be null");
		Objects.requireNonNull(query, "Query must not be null");

		query.setFirstResult(page.getOffset());
		query.setMaxResults(page.getLimit());
	}

	/**
	 * Sets the SearchFacade component, mainly for testing purposes.
	 *
	 * @param searchFacade the {@link SearchFacade} instance to set; must not be
	 *                     {@code null}
	 * @throws NullPointerException if {@code searchFacade} is {@code null}
	 */
	public void setSearchFacade(final SearchFacade searchFacade) {
		this.searchFacade = Objects.requireNonNull(searchFacade, "SearchFacade must not be null");
	}
}
