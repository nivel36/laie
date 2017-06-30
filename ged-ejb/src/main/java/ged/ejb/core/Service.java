package ged.ejb.core;

import java.util.List;

import ged.ejb.core.model.Identificable;

public interface Service<K, T extends Identificable<? extends K>> {

	void delete(T entity);

	T find(K id);

	List<T> findAll();

	T save(T entity);
}