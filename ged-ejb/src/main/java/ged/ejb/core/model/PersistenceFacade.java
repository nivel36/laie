package ged.ejb.core.model;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Local
public interface PersistenceFacade {

	void clear();

	<K, T extends Entity<K>> boolean contains(T entity);

	<K, T extends Entity<K>> void delete(T entity);

	<K, T extends Entity<K>> void detach(T entity);

	void flush();

	<K, T extends Entity<K>> List<T> fullSearch(final Class<T> clazz, final List<String> fields,
			final List<String> matching);

	<K, T extends Entity<K>> List<T> getAll(Class<T> clazz);

	<T> List<T> getByCriteria(CriteriaQuery<T> cq, int pageSize, int pageNum);

	<T> T getByCriteriaSingleResult(CriteriaQuery<T> cq);

	<K, T extends Entity<K>> T getByPrimaryKey(Class<T> entityClass, K id);

	<K, T extends Entity<K>> T getByProperties(Class<T> clazz, Map<String, Object> properties);

	<K, T extends Entity<K>> List<T> getByProperties(Class<T> clazz, Map<String, Object> properties, int pageSize,
			int pageNum);

	List<?> getByQuery(String nombreQuery, Map<String, Object> parameters, int pageSize, int pageNum);

	Object getByQuerySingleResult(String nombreQuery, Map<String, Object> parameters);

	<K, T extends Entity<K>> List<T> getByTypedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	<K, T extends Entity<K>> T getByTypedQuerySingleResult(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);

	CriteriaBuilder getCriteriaBuilder();

	EntityManager getEm();

	<K, T extends Entity<K>> T getReference(Class<T> entityClass, Object primaryKey);

	<K, T extends Entity<K>> void insert(T entity);

	<K, T extends Entity<K>> void lock(T entity, LockModeType lockModeType, Map<String, Object> properties);

	<K, T extends Entity<K>> void refresh(T entity, LockModeType lockModeType, Map<String, Object> properties);

	<K, T extends Entity<K>> T update(T entity);

}