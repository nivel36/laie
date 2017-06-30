package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.User;

public interface ActionService extends Service<Action> {

	void deleteAction(final AuditedEntity auditedEntity);

	List<Action> findAllByUser(final User user);

	void insertAction(final AuditedEntity auditedEntity);

	void undeleteAction(final AuditedEntity auditedEntity);

	void updateAction(final AuditedEntity auditedEntity);
}