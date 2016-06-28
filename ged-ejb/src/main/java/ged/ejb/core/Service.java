package ged.ejb.core;

import java.util.List;

import ged.ejb.core.model.Entity;

public interface Service<K, T extends Entity<? extends K>> {

	void delete(T entity);

	T find(K id);

	List<T> findAll();

	void insert(T entity);

	T update(T entity);
}