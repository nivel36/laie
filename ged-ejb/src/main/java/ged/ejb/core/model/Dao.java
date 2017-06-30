package ged.ejb.core.model;

import java.util.List;

public interface Dao<T extends Identificable> {

	void delete(T entity);

	T find(long id);

	List<T> findAll();

	void insert(T entity);

	T update(T entity);
}