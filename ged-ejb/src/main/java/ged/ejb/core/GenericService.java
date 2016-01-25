package ged.ejb.core;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.AuditedEntity;

@Local
public interface GenericService {
	
	public <T extends AbstractEntity> T insertOrUpdate(T entity);

	public <T extends AbstractEntity> void insert(T entity);

	public <T extends AbstractEntity> T update(T entity);

	public <T extends AbstractEntity> void delete(T entity);

	public <T extends AuditedEntity> void softDelete(T entity);

	public <T extends AbstractEntity> List<T> getByTypedQuery(Class<T> clazz,
			String queryName, Map<String, Object> params);

	public <T extends AbstractEntity> T getByPrimaryKey(Class<T> clazz, Long id);

	public List<?> getByQuery(String nombreQuery, Map<String, Object> parameters);

	public List<?> getByQuery(String nombreQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	public Object getByQuerySingleResult(String nombreQuery);

	public Object getByQuerySingleResult(String nombreQuery,
			Map<String, Object> parameters);

	public <T extends AbstractEntity> List<T> getByTypedQuery(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum);

	public <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery);

	public <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters);
	
	public <T extends AbstractEntity> List<T> getAll(Class<T> clazz);
	
	public abstract <T extends AbstractEntity> List<T> getByProperties(
			Class<T> clazz, Map<String, Object> properties, int pageSize,
			int pageNum);

	public abstract <T extends AbstractEntity> T getByProperties(
			Class<T> clazz, Map<String, Object> properties);

}