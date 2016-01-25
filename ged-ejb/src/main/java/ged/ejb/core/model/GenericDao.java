package ged.ejb.core.model;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface GenericDao {

	public abstract <T extends AbstractEntity> void delete(T entity);

	public abstract <T extends AbstractEntity> void insert(T entity);

	public abstract <T extends AbstractEntity> T update(T entity);

	public abstract <T extends AbstractEntity> T getByPrimaryKey(
			Class<T> entityClass, Long id);

	public abstract List<?> getByQuery(String nombreQuery,
			Map<String, Object> parameters);

	public abstract List<?> getByQuery(String nombreQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	public abstract Object getByQuerySingleResult(String nombreQuery);

	public abstract Object getByQuerySingleResult(String nombreQuery,
			Map<String, Object> parameters);

	public abstract <T extends AbstractEntity> List<T> getByTypedQuery(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);

	public abstract <T extends AbstractEntity> List<T> getByTypedQuery(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	public abstract <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery);

	public abstract <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);

	public abstract <T extends AuditedEntity> void softDelete(T entity);

	public abstract <T extends AuditedEntity> void undelete(T entity);

	public abstract <T extends AbstractEntity> List<T> getAll(Class<T> clazz);

	public abstract <T extends AbstractEntity> List<T> getByProperties(
			Class<T> clazz, Map<String, Object> properties, int pageSize,
			int pageNum);

	public abstract <T extends AbstractEntity> T getByProperties(
			Class<T> clazz, Map<String, Object> properties);

}