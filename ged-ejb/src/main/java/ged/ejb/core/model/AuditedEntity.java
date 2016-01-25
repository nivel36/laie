package ged.ejb.core.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AuditedEntity extends AbstractEntity {

	private static final long serialVersionUID = 6203444960560029390L;
	
	@Column(nullable=true)
	private Boolean deleted;

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}
