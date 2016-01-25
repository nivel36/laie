package ged.web.core.view;

import javax.annotation.PostConstruct;

import ged.ejb.core.model.AuditedEntity;

public abstract class AbstractEditBean<T extends AuditedEntity> extends
		AbstractBean {

	private static final long serialVersionUID = 7342938011321162420L;

	private T entity;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		String entityClass = entity.getClass().getSimpleName();
		if (flash.containsKey(entityClass)) {
			entity = (T) flash.get(entityClass);
		}
		doInit();
	}

	public abstract void doInit();

	public abstract String back();

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public String save() {
		//genericService.insertOrUpdate(entity);
		return back() + "?faces-redirect=true";
	}

	public String cancel() {
		return back() + "?faces-redirect=true";
	}
}
