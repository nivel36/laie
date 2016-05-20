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

	public abstract void clear();

	public abstract <T extends AbstractEntity> boolean contains(T entity);

	public abstract <T extends AbstractEntity> void delete(T entity);

	public abstract <T extends AbstractEntity> void detach(T entity);

	public abstract void flush();

	public abstract <T extends AbstractEntity> List<T> fullSearch(final Class<T> clazz, final List<String> fields,
			final List<String> matching);

	public abstract <T extends AbstractEntity> List<T> fullSearch(Class<T> clazz, String matching, String... fields);

	public abstract <T extends AbstractEntity> List<T> getAll(Class<T> clazz);

	public abstract <T> List<T> getByCriteria(CriteriaQuery<T> cq, int pageSize, int pageNum);

	public abstract <T> T getByCriteriaSingleResult(CriteriaQuery<T> cq);

	public abstract <T extends AbstractEntity> T getByPrimaryKey(Class<T> entityClass, Long id);

	public abstract <T extends AbstractEntity> T getByProperties(Class<T> clazz, Map<String, Object> properties);

	public abstract <T extends AbstractEntity> List<T> getByProperties(Class<T> clazz, Map<String, Object> properties,
			int pageSize, int pageNum);

	public abstract List<?> getByQuery(String nombreQuery, Map<String, Object> parameters, int pageSize, int pageNum);

	public abstract Object getByQuerySingleResult(String nombreQuery, Map<String, Object> parameters);

	public abstract <T extends AbstractEntity> List<T> getByTypedQuery(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	public abstract <T extends AbstractEntity> T getByTypedQuerySingleResult(Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);

	public abstract CriteriaBuilder getCriteriaBuilder();

	public abstract <T extends AbstractEntity> T getReference(Class<T> entityClass, Object primaryKey);

	public abstract <T extends AbstractEntity> void insert(T entity);

	public abstract <T extends AbstractEntity> void lock(T entity, LockModeType lockModeType,
			Map<String, Object> properties);

	public abstract <T extends AbstractEntity> void refresh(T entity, LockModeType lockModeType,
			Map<String, Object> properties);

	public abstract void setEm(EntityManager em);

	public abstract void setLogger(Logger logger);

	public abstract <T extends AbstractEntity> T update(T entity);

}