package ged.ejb.core.model;

import java.util.List;

public interface CrudDao<K, T extends Entity<K>> {

	void delete(T entity);

	T find(K id);

	List<T> findAll();

	void insert(T entity);

	T update(T entity);
}