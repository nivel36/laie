package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

public abstract class AbstractDaoJpa<T extends AbstractEntity> implements Dao<T> {

	@Inject
	@Repository
	private PersistenceFacade pf;

	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		this.pf.delete(this.getType(), entity);
	}

	@Override
	public T find(final long id) {
		return this.pf.find(this.getType(), id);
	}

	@Override
	public List<T> findAll() {
		return this.pf.findAll(this.getType());
	}

	public <E> List<E> findAll(final Class<E> type) {
		Objects.requireNonNull(type);
		return this.pf.findAll(type);
	}

	protected <E> List<E> findByQuery(final Class<E> entityClass, final String namedQuery, final Integer pageSize,
			final Integer pageNum) {
		return this.findByTypedQuery(entityClass, namedQuery, null, pageSize, pageNum);
	}

	protected <E> E findByQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		return this.pf.findByTypedQuery(entityClass, namedQuery, parameters);
	}

	public Object findByQuery(final String namedQuery) {
		return this.findByQuery(namedQuery, null);
	}

	public Object findByQuery(final String namedQuery, final Map<String, Object> parameters) {
		Objects.requireNonNull(namedQuery);
		return this.pf.findByQuery(namedQuery, parameters);
	}

	protected <E> List<E> findByTypedQuery(final Class<E> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final Integer pageSize, final Integer pageNum) {
		Objects.requireNonNull(entityClass);
		Objects.requireNonNull(namedQuery);
		return this.pf.findByTypedQuery(entityClass, namedQuery, parameters, pageSize, pageNum);
	}

	protected PersistenceFacade getPf() {
		return this.pf;
	}

	protected abstract Class<T> getType();

	@Override
	public void insert(final T entity) {
		Objects.requireNonNull(entity);
		this.pf.insert(entity);
	}

	public abstract List<T> search(final String searchText);

	public void setPf(final PersistenceFacade pf) {
		this.pf = pf;
	}

	@Override
	public T update(final T entity) {
		Objects.requireNonNull(entity);
		return this.pf.update(entity);
	}
}