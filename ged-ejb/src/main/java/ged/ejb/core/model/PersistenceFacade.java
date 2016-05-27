package ged.ejb.core.model;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Local
public interface PersistenceFacade {

	void clear();

	<T extends AbstractEntity> boolean contains(T entity);

	<T extends AbstractEntity> void delete(T entity);

	<T extends AbstractEntity> void detach(T entity);

	void flush();

	<T extends AbstractEntity> List<T> fullSearch(final Class<T> clazz, final List<String> fields,
			final List<String> matching);

	<T extends AbstractEntity> List<T> fullSearch(Class<T> clazz, String matching, String... fields);

	<T extends AbstractEntity> List<T> getAll(Class<T> clazz);

	<T> List<T> getByCriteria(CriteriaQuery<T> cq, int pageSize, int pageNum);

	<T> T getByCriteriaSingleResult(CriteriaQuery<T> cq);

	<T extends AbstractEntity> T getByPrimaryKey(Class<T> entityClass, Long id);

	<T extends AbstractEntity> T getByProperties(Class<T> clazz, Map<String, Object> properties);

	<T extends AbstractEntity> List<T> getByProperties(Class<T> clazz, Map<String, Object> properties, int pageSize,
			int pageNum);

	List<?> getByQuery(String nombreQuery, Map<String, Object> parameters, int pageSize, int pageNum);

	Object getByQuerySingleResult(String nombreQuery, Map<String, Object> parameters);

	<T extends AbstractEntity> List<T> getByTypedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	<T extends AbstractEntity> T getByTypedQuerySingleResult(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);

	CriteriaBuilder getCriteriaBuilder();

	EntityManager getEm();

	<T extends AbstractEntity> T getReference(Class<T> entityClass, Object primaryKey);

	<T extends AbstractEntity> void insert(T entity);

	<T extends AbstractEntity> void lock(T entity, LockModeType lockModeType, Map<String, Object> properties);

	<T extends AbstractEntity> void refresh(T entity, LockModeType lockModeType, Map<String, Object> properties);

	void setEm(EntityManager em);

	void setLogger(Logger logger);

	<T extends AbstractEntity> T update(T entity);

}