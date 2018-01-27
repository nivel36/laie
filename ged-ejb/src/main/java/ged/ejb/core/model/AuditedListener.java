package ged.ejb.core.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditedListener {

	@PrePersist
	public void prePersist(final AbstractAuditedEntity entity) {
		// nothing to do now
	}

	@PreUpdate
	public void preUpdate(final AbstractAuditedEntity entity) {
		// nothing to do now
	}

}