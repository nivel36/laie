package ged.ejb.core.model;

import java.util.List;

import javax.inject.Inject;

@Repository
public abstract class AbstractCrudDao<K, T extends Entity<K>> implements CrudDao<K, T> {

	@Inject
	@Repository
	protected PersistenceFacade persistenceFacade;

	@Override
	public void delete(final T entity) {
		this.persistenceFacade.delete(entity);
	}

	@Override
	public T find(final K id) {
		return this.persistenceFacade.getByPrimaryKey(getClazz(), id);
	}

	@Override
	public List<T> findAll() {
		return this.persistenceFacade.getAll(getClazz());
	}

	public abstract Class<T> getClazz();

	@Override
	public void insert(final T entity) {
		this.persistenceFacade.insert(entity);
	}

	@Override
	public T update(final T entity) {
		return this.persistenceFacade.update(entity);
	}
}