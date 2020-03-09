package ged.ejb.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

public abstract class AbstractDao<T extends AbstractEntity> {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		this.persistenceFacade.delete(this.getType(), entity);
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

	protected <E> List<E> findByQuery(final Class<E> entityClass, final String namedQuery, final Page page) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		try {
			return this.persistenceFacade.findByQuery(entityClass, namedQuery, null, page);
		} catch (final NoResultException e) {
			return new ArrayList<>();
		}
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

	protected PersistenceFacade getPersistenceFacade() {
		return this.persistenceFacade;
	}

	protected abstract Class<T> getType();

	protected T insert(final T entity) {
		this.preInsert(entity);
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

	public void setPersistenceFacade(final PersistenceFacade persistenceFacade) {
		Objects.requireNonNull(persistenceFacade);
		this.persistenceFacade = persistenceFacade;
	}

	protected T update(final T entity) {
		this.preUpdate(entity);
		final T updatedEntity = this.persistenceFacade.update(entity);
		this.postUpdate(entity);
		return updatedEntity;
	}
}