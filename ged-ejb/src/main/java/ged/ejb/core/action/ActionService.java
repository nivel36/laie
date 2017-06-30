package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.user.User;

public interface ActionService extends Service<Action> {

	void deleteAction(final AbstractAuditedEntity auditedEntity);

	List<Action> findAllByUser(final User user);

	void insertAction(final AbstractAuditedEntity auditedEntity);

	void undeleteAction(final AbstractAuditedEntity auditedEntity);

	void updateAction(final AbstractAuditedEntity auditedEntity);
}