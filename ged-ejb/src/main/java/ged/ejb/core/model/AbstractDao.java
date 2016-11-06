package ged.ejb.core.model;

import java.util.List;
import java.util.Objects;

@Repository
public abstract class AbstractDao<K, T extends Entity<K>> implements Dao<K, T> {

	protected final PersistenceFacade persistenceFacade;

	protected AbstractDao(final PersistenceFacade persistenceFacade) {
		Objects.requireNonNull(persistenceFacade);
		this.persistenceFacade = persistenceFacade;
	}

	@Override
	public void delete(final T entity) {
		Objects.requireNonNull(entity);
		this.persistenceFacade.delete(entity);
	}

	@Override
	public T find(final K id) {
		Objects.requireNonNull(id);
		return this.persistenceFacade.find(getClazz(), id);
	}

	@Override
	public List<T> findAll() {
		return this.persistenceFacade.findAll(getClazz());
	}

	public abstract Class<T> getClazz();

	@Override
	public void insert(final T entity) {
		Objects.requireNonNull(entity);
		this.persistenceFacade.insert(entity);
	}

	@Override
	public T update(final T entity) {
		Objects.requireNonNull(entity);
		return this.persistenceFacade.update(entity);
	}
}