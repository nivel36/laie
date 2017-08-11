package ged.ejb.core.action;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ged.ejb.core.model.AbstractRecordEntity;

public class ActionListener {

	@PrePersist
	public void prePersist(final AbstractRecordEntity entity) {
	}

	@PreUpdate
	public void preUpdate(final AbstractRecordEntity entity) {
	}
}