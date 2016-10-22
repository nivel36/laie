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

	<K, T extends Entity<K>> List<T> findAll(Class<T> clazz);

	<T> T findByCriteria(CriteriaQuery<T> cq);

	<T> List<T> findByCriteria(CriteriaQuery<T> cq, int pageSize, int pageNum);

	<K, T extends Entity<K>> T findByProperties(Class<T> clazz, Map<String, Object> properties);

	<K, T extends Entity<K>> List<T> findByProperties(Class<T> clazz, Map<String, Object> properties, int pageSize,
			int pageNum);

	Object findByQuery(String nombreQuery, Map<String, Object> parameters);

	List<Object> findByQuery(String nombreQuery, Map<String, Object> parameters, int pageSize, int pageNum);

	<T> T findByTypedQuery(Class<T> entityClass, String namedQuery, Map<String, Object> parameters);

	<T> List<T> findByTypedQuery(Class<T> entityClass, String namedQuery, Map<String, Object> parameters, int pageSize,
			int pageNum);

	EntityManager getEm();

	<K, T extends Entity<K>> void insert(T entity);

	<K, T extends Entity<K>> T update(T entity);

}