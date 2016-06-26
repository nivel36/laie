package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.CrudService;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.User;

public interface ActionService extends CrudService<Long, Action> {

	void addAction(final AuditedEntity auditedEntity, final String actionType);

	List<Action> findAllByUser(final User user);

	void removeAction(final AuditedEntity auditedEntity);
}