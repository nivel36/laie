package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

public abstract class AbstractDaoJpa<T extends AbstractEntity> implements Dao<T> {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		this.persistenceFacade.delete(this.getType(), entity);
	}

	@Override
	public T find(final long id) {
		if (id <= 0) {
			throw new IllegalArgumentException("id: " + id);
		}
		return this.persistenceFacade.find(this.getType(), id);
	}

	@Override
	public List<T> findAll() {
		return this.persistenceFacade.findAll(this.getType());
	}

	public <E> List<E> findAll(final Class<E> type) {
		Objects.requireNonNull(type);
		return this.persistenceFacade.findAll(type);
	}

	protected <E> E findByQuery(final Class<E> entityClass, final String namedQuery) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		return this.persistenceFacade.findByQuery(entityClass, namedQuery, null);
	}

	protected <E> List<E> findByQuery(final Class<E> entityClass, final String namedQuery, final Integer pageSize,
			final Integer pageNum) {
		return this.findByQuery(entityClass, namedQuery, null, pageSize, pageNum);
	}

	protected <E> E findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		return this.persistenceFacade.findByQuery(entityClass, namedQuery, parameters);
	}

	protected <E> List<E> findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final Integer pageSize, final Integer pageNum) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		return this.persistenceFacade.findByQuery(entityClass, namedQuery, parameters, pageSize, pageNum);
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

	@Override
	public void insert(final T entity) {
		Objects.requireNonNull(entity);
		this.persistenceFacade.insert(entity);
	}

	public void setPersistenceFacade(final PersistenceFacade persistenceFacade) {
		Objects.requireNonNull(persistenceFacade);
		this.persistenceFacade = persistenceFacade;
	}

	@Override
	public T update(final T entity) {
		Objects.requireNonNull(entity);
		return this.persistenceFacade.update(entity);
	}
}