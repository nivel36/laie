package ged.web.core.view;

import javax.annotation.PostConstruct;

import ged.ejb.core.model.AuditedEntity;

public abstract class AbstractEditBean<T extends AuditedEntity<Long>> extends AbstractPageBean {

	private static final long serialVersionUID = 7342938011321162420L;

	private T entity;

	public abstract String back();

	public String cancel() {
		return back() + "?faces-redirect=true";
	}

	public abstract void doInit();

	public T getEntity() {
		return this.entity;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		final String entityClass = this.entity.getClass().getSimpleName();
		if (this.flash.containsKey(entityClass)) {
			this.entity = (T) this.flash.get(entityClass);
		}
		doInit();
	}

	public String save() {
		// genericService.insertOrUpdate(entity);
		return back() + "?faces-redirect=true";
	}

	public void setEntity(final T entity) {
		this.entity = entity;
	}
}
