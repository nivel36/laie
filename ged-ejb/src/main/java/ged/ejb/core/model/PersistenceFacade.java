package ged.ejb.core.model;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

@Local
public interface PersistenceFacade {

	<K, T extends Entity<K>> void delete(T entity);

	<K, T extends Entity<K>> T find(Class<T> entityClass, K id);

	<T> T findByCriteria(CriteriaQuery<T> cq);

	<K, T extends Entity<K>> T findByProperties(Class<T> clazz, Map<String, Object> properties);

	<K, T extends Entity<K>> List<T> findByProperties(Class<T> clazz, Map<String, Object> properties, int pageSize,
			int pageNum);

	List<?> findByQuery(String nombreQuery, Map<String, Object> parameters, int pageSize, int pageNum);

	Object findByQuery(String nombreQuery, Map<String, Object> parameters);

	<K, T extends Entity<K>> List<T> findByTypedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	<K, T extends Entity<K>> T findByTypedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);

	<K, T extends Entity<K>> List<T> fullSearch(final Class<T> clazz, final List<String> fields,
			final List<String> matching);

	<K, T extends Entity<K>> List<T> findAll(Class<T> clazz);

	<T> List<T> findByCriteria(CriteriaQuery<T> cq, int pageSize, int pageNum);

	EntityManager getEm();

	<K, T extends Entity<K>> void insert(T entity);

	<K, T extends Entity<K>> T update(T entity);

}