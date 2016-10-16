package ged.ejb.core.model;

import java.util.List;

@Repository
public abstract class AbstractDao<K, T extends Entity<K>> implements Dao<K, T> {

	protected final PersistenceFacade persistenceFacade;

	protected AbstractDao(final PersistenceFacade persistenceFacade) {
		if (persistenceFacade == null) {
			throw new NullPointerException("persistanceFacade");
		}
		this.persistenceFacade = persistenceFacade;
	}

	@Override
	public void delete(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		this.persistenceFacade.delete(entity);
	}

	@Override
	public T find(final K id) {
		if (id == null) {
			throw new NullPointerException();
		}
		return this.persistenceFacade.find(getClazz(), id);
	}

	@Override
	public List<T> findAll() {
		return this.persistenceFacade.findAll(getClazz());
	}

	public abstract Class<T> getClazz();

	@Override
	public void insert(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		this.persistenceFacade.insert(entity);
	}

	@Override
	public T update(final T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		return this.persistenceFacade.update(entity);
	}
}