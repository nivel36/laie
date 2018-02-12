package ged.ejb.core;

import java.util.List;

import ged.ejb.core.model.Identificable;

public interface Service<T extends Identificable> {

	void delete(T entity);

	T find(long id);

	List<T> findAll();

	void insert(T entity);

	T update(T entity);

	List<T> search(String searchText);
}