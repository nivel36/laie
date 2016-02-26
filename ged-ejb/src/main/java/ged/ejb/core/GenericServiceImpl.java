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
	public <T extends AbstractEntity> void delete(final T entity) {
		this.genericDao.delete(entity);
	}

	@Override
	public <T extends AbstractEntity> List<T> getAll(final Class<T> clazz) {
		return this.genericDao.getAll(clazz);
	}

	@Override
	public <T extends AbstractEntity> T getByPrimaryKey(final Class<T> clazz, final Long id) {
		return this.genericDao.getByPrimaryKey(clazz, id);
	}

	@Override
	public <T extends AbstractEntity> T getByProperties(final Class<T> clazz, final Map<String, Object> properties) {
		return this.genericDao.getByProperties(clazz, properties);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByProperties(final Class<T> clazz,
			final Map<String, Object> properties, final int pageSize, final int pageNum) {
		return this.genericDao.getByProperties(clazz, properties, pageSize, pageNum);
	}

	@Override
	public List<?> getByQuery(final String nombreQuery, final Map<String, Object> parameters) {
		return this.genericDao.getByQuery(nombreQuery, parameters);
	}

	@Override
	public List<?> getByQuery(final String nombreQuery, final Map<String, Object> parameters, final int pageSize,
			final int pageNum) {
		return this.genericDao.getByQuery(nombreQuery, parameters, pageSize, pageNum);
	}

	@Override
	public Object getByQuerySingleResult(final String nombreQuery) {
		return getByQuerySingleResult(nombreQuery);
	}

	@Override
	public Object getByQuerySingleResult(final String nombreQuery, final Map<String, Object> parameters) {
		return getByQuerySingleResult(nombreQuery, parameters);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(final Class<T> clazz, final String queryName,
			final Map<String, Object> params) {
		return this.genericDao.getByTypedQuery(clazz, queryName, params);
	}

	@Override
	public <T extends AbstractEntity> List<T> getByTypedQuery(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters, final int pageSize, final int pageNum) {
		return this.genericDao.getByTypedQuery(entityClass, namedQuery, parameters, pageSize, pageNum);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(final Class<T> entityClass,
			final String namedQuery) {
		return this.genericDao.getByTypedQuerySingleResult(entityClass, namedQuery);
	}

	@Override
	public <T extends AbstractEntity> T getByTypedQuerySingleResult(final Class<T> entityClass, final String namedQuery,
			final Map<String, Object> parameters) {
		return this.genericDao.getByTypedQuerySingleResult(entityClass, namedQuery, parameters);
	}

	@Override
	public <T extends AbstractEntity> void insert(final T entity) {
		this.genericDao.insert(entity);
	}

	@Override
	public <T extends AbstractEntity> T insertOrUpdate(final T entity) {
		T returnedEntity = null;
		if (entity.getId() == 0) {
			this.genericDao.insert(entity);
			returnedEntity = entity;
		} else {
			returnedEntity = this.genericDao.update(entity);
		}
		return returnedEntity;
	}

	@Override
	public <T extends AuditedEntity> void softDelete(final T entity) {
		this.genericDao.softDelete(entity);
	}

	@Override
	public <T extends AbstractEntity> T update(final T entity) {
		return this.genericDao.update(entity);
	}
}
