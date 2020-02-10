package ged.ejb.core.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ged.ejb.core.model.search.SearchFacets;
import ged.ejb.core.model.search.SearchResult;
import ged.ejb.core.model.search.SortField;

public abstract class AbstractDao<T extends AbstractEntity> {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		this.persistenceFacade.delete(this.getType(), entity);
	}

	protected boolean existUid(final String uidCandidate) {
		final CriteriaBuilder cb = this.getPersistenceFacade().getEm().getCriteriaBuilder();
		final CriteriaQuery<Long> q = cb.createQuery(Long.class);
		final Root<T> c = q.from(this.getType());
		final Predicate predicate = cb.equal(c.get("uid"), uidCandidate);
		q.select(cb.count(c)).where(predicate);
		return this.getPersistenceFacade().getEm().createQuery(q).getSingleResult() > 0;
	}

	public T find(final long id) {
		if (id <= 0) {
			throw new IllegalArgumentException("id: " + id);
		}
		return this.persistenceFacade.find(this.getType(), id);
	}

	public <E> List<E> findAll(final Class<E> type, final Page page) {
		Objects.requireNonNull(type);
		return this.persistenceFacade.findAll(type, page);
	}

	public List<T> findAll(final Page page) {
		return this.persistenceFacade.findAll(this.getType(), page);
	}

	protected <E> E findByQuery(final Class<E> entityClass, final String namedQuery) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		return this.persistenceFacade.findByQuery(entityClass, namedQuery, null);
	}

	protected <E> E findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		return this.persistenceFacade.findByQuery(entityClass, namedQuery, parameters);
	}

	protected <E> E findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final FlushModeType flushModeType) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		return this.persistenceFacade.findByQuery(entityClass, namedQuery, parameters, flushModeType);
	}

	protected <E> List<E> findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final Page page) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		try {
			return this.persistenceFacade.findByQuery(entityClass, namedQuery, parameters, page);
		} catch (final NoResultException e) {
			return new ArrayList<>();
		}
	}

	protected Object findByQuery(final String namedQuery) {
		return this.findByQuery(namedQuery, null);
	}

	protected Object findByQuery(final String namedQuery, final Map<String, Object> parameters) {
		Objects.requireNonNull(namedQuery);
		return this.persistenceFacade.findByQuery(namedQuery, parameters);
	}

	protected String generateUid() {
		boolean uidExist = false;
		String uidCandidate;
		final Random random = ThreadLocalRandom.current();
		final byte[] bytes = new byte[4];
		do {
			random.nextBytes(bytes);
			uidCandidate = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
			uidExist = this.existUid(uidCandidate);
		} while (uidExist);
		return uidCandidate;
	}

	protected PersistenceFacade getPersistenceFacade() {
		return this.persistenceFacade;
	}

	protected abstract Class<T> getType();

	protected T insert(final T entity) {
		this.preInsert(entity);
		this.setUid(entity);
		this.persistenceFacade.insert(entity);
		this.postInsert(entity);
		return entity;
	}

	protected void postInsert(final T entity) {
	}

	protected void postUpdate(final T entity) {
	}

	protected void preInsert(final T entity) {
	}

	protected void preUpdate(final T entity) {
	}

	public T save(final T entity) {
		Objects.requireNonNull(entity);
		if (entity.getId() == 0) {
			return this.insert(entity);
		} else {
			return this.update(entity);
		}
	}

	public SearchResult<T> search(final String searchText, final Page page) {
		return this.search(searchText, page, new ArrayList<SortField>(), null);
	}

	public SearchResult<T> search(final String searchText, final Page page, final List<SortField> sortOrders,
			final SearchFacets searchFacets) {
		return this.persistenceFacade.search(this.getType(), page, sortOrders, searchFacets, searchText,
				this.searchFields());
	}

	public SearchResult<T> search(final String searchText, final Page page, final SortField sortOrder,
			final SearchFacets searchFacets) {
		final List<SortField> sortOrders = new ArrayList<>();
		if (sortOrder != null) {
			sortOrders.add(sortOrder);
		}
		return this.search(searchText, page, sortOrders, searchFacets);
	}

	public abstract String[] searchFields();

	public void setPersistenceFacade(final PersistenceFacade persistenceFacade) {
		Objects.requireNonNull(persistenceFacade);
		this.persistenceFacade = persistenceFacade;
	}

	private void setUid(final T entity) {
		if (Obfuscable.class.isAssignableFrom(entity.getClass())) {
			final Obfuscable o = (Obfuscable) entity;
			final String base64Id = this.generateUid();
			o.setUid(base64Id);
		}
	}

	protected T update(final T entity) {
		this.preUpdate(entity);
		final T updatedEntity = this.persistenceFacade.update(entity);
		this.postUpdate(entity);
		return updatedEntity;
	}
}