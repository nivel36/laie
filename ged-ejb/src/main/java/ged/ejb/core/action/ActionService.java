package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.User;

public interface ActionService extends Service<Long, Action> {

	void addAction(final AuditedEntity auditedEntity, final String actionType);

	List<Action> findAllByUser(final User user);

}