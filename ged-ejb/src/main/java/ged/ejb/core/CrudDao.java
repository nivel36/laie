package ged.ejb.core;

import java.util.List;

import ged.ejb.core.model.Entity;

public interface CrudDao<K, T extends Entity<K>> {

	void delete(T entity);

	T find(K id);

	List<T> findAll();

	void insert(T entity);

	T update(T entity);
}