package ged.ejb.core.action;

import java.util.List;

import ged.ejb.core.Service;
import ged.ejb.core.model.Auditable;
import ged.ejb.user.User;

public interface ActionService extends Service<Action> {

	void deleteAction(final Auditable auditedEntity);

	List<Action> findAllByUser(final User user);

	void insertAction(final Auditable auditedEntity);

	void undeleteAction(final Auditable auditedEntity);

	void updateAction(final Auditable auditedEntity);
}