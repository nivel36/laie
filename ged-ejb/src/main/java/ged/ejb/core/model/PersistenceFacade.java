package ged.ejb.core.model;

import java.util.List;
import java.util.Map;

public interface PersistenceFacade {

	<T extends Identificable> void delete(Class<T> type, T entity);

	<T extends Identificable> T find(Class<T> type, long id);

	<E> List<E> findAll(Class<E> type);

	<E> List<E> findByQuery(Class<E> entityClass, String namedQuery, Integer pageSize, Integer pageNum);

	<E> E findByQuery(Class<E> entityClass, String namedQuery, Map<String, Object> parameters);

	<E> List<E> findByQuery(Class<E> entityClass, String namedQuery, Map<String, Object> parameters, Integer pageSize,
			Integer pageNum);

	Object findByQuery(String namedQuery);

	Object findByQuery(String namedQuery, Map<String, Object> parameters);

	<T extends Identificable> void insert(T entity);

	<T extends Identificable> List<T> search(final Class<T> type, final String searchText, final String... fields);

	<T extends Identificable> T update(T entity);

}