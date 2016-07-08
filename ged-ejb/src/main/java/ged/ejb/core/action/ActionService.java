package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.User;

public interface ActionService extends Service<Long, Action> {

	void deleteAction(final AuditedEntity<Long> auditedEntity);

	List<Action> findAllByUser(final User user);

	void insertAction(final AuditedEntity<Long> auditedEntity);

	void undeleteAction(final AuditedEntity<Long> auditedEntity);

	void updateAction(final AuditedEntity<Long> auditedEntity);

}