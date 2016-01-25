package ged.ejb.core;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import ged.ejb.core.model.AbstractEntity;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.model.GenericDao;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GenericServiceImpl implements GenericService {

	@Inject
	private GenericDao genericDao;

	@Override
	public <T extends AbstractEntity> T insertOrUpdate(T entity) {
		T returnedEntity = null;
		if (entity.getId() == 0) {
			genericDao.insert(entity);
			returnedEntity = entity;
		} else {
			returnedEntity = genericDao.update(entity);
		}
		return returnedEntity;
	}

	@Override
	public <T extends AbstractEntity> void insert(T entity) {
		genericDao.insert(entity);
	}

	@Override
	public <T extends AbstractEntity> T update(T entity) {
		return genericDao.update(entity);
	}

	@Override
	public <T extends AbstractEntity> void delete(T entity) {
		genericDao.delete(entity);
	}

	@Override
	public <T extends AuditedEntity> void softDelete(T entity) {
		genericDao.softDelete(entity);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(Class<T> clazz,
			String queryName, Map<String, Object> params) {
		return genericDao.getByTypedQuery(clazz, queryName, params);
	}

	@Override
	public <T extends AbstractEntity> T getByPrimaryKey(Class<T> clazz,
			Long id) {
		return genericDao.getByPrimaryKey(clazz, id);
	}

	@Override
	public List<?> getByQuery(String nombreQuery, Map<String, Object> parameters) {
		return genericDao.getByQuery(nombreQuery, parameters);
	}

	@Override
	public List<?> getByQuery(String nombreQuery,
			Map<String, Object> parameters, int pageSize, int pageNum) {
		return genericDao
				.getByQuery(nombreQuery, parameters, pageSize, pageNum);
	}

	@Override
	public Object getByQuerySingleResult(String nombreQuery) {
		return getByQuerySingleResult(nombreQuery);
	}

	@Override
	public Object getByQuerySingleResult(String nombreQuery,
			Map<String, Object> parameters) {
		return getByQuerySingleResult(nombreQuery, parameters);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters, int pageSize, int pageNum) {
		return genericDao.getByTypedQuery(entityClass, namedQuery, parameters,
				pageSize, pageNum);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery) {
		return genericDao.getByTypedQuerySingleResult(entityClass, namedQuery);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(
			Class<T> entityClass, String namedQuery,
			Map<String, Object> parameters) {
		return genericDao.getByTypedQuerySingleResult(entityClass, namedQuery,
				parameters);
	}

	@Override
	public <T extends AbstractEntity> List<T> getAll(Class<T> clazz) {
		return genericDao.getAll(clazz);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByProperties(Class<T> clazz,
			Map<String, Object> properties, int pageSize, int pageNum) {
		return genericDao.getByProperties(clazz, properties, pageSize, pageNum);
	}

	@Override
	public <T extends AbstractEntity> T getByProperties(Class<T> clazz,
			Map<String, Object> properties) {
		return genericDao.getByProperties(clazz, properties);
	}
}
