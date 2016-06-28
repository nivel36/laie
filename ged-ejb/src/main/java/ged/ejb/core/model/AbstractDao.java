package ged.ejb.core.model;

import java.util.List;

import javax.inject.Inject;

@Repository
public abstract class AbstractDao<K, T extends Entity<K>> implements Dao<K, T> {

	@Inject
	@Repository
	protected PersistenceFacade persistenceFacade;

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
		return this.persistenceFacade.getByPrimaryKey(getClazz(), id);
	}

	@Override
	public List<T> findAll() {
		return this.persistenceFacade.getAll(getClazz());
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