package ged.ejb.core.action;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.events.PostDelete;
import ged.ejb.core.events.PostPersist;
import ged.ejb.core.events.PostUndelete;
import ged.ejb.core.events.PostUpdate;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Stateless
public class ActionServiceImpl extends AbstractService<Long, Action> implements ActionService {

	@Inject
	@Repository
	private ActionDao actionDao;

	@Override
	public void deleteAction(@PostDelete @Observes final AuditedEntity<Long> auditedEntity) {
		insertAction(auditedEntity, Action.DELETE);
	}

	@Override
	public List<Action> findAllByUser(final User user) {
		return this.actionDao.findAllByUser(user);
	}

	private Action getActionFromEntity(final AuditedEntity<Long> auditedEntity) {
		final Action action = new Action();
		action.setEntityId(auditedEntity.getId());
		action.setEntityClass(auditedEntity.getClass().getSimpleName());
		action.setText(auditedEntity.toString());
		action.setUser(auditedEntity.getUser());
		return action;
	}

	@Override
	protected ActionDao getDao() {
		return this.actionDao;
	}

	@Override
	public void insertAction(@PostPersist @Observes final AuditedEntity<Long> auditedEntity) {
		insertAction(auditedEntity, Action.INSERT);
	}

	private void insertAction(final AuditedEntity<Long> auditedEntity, final String actionType) {
		final Action action = getActionFromEntity(auditedEntity);
		action.setActionPerformed(actionType);
		action.setDate(new Date());
		insert(action);
	}

	@Override
	public void undeleteAction(@PostUndelete @Observes final AuditedEntity<Long> auditedEntity) {
		insertAction(auditedEntity, Action.UNDELETE);
	}

	@Override
	public void updateAction(@PostUpdate @Observes final AuditedEntity<Long> auditedEntity) {
		insertAction(auditedEntity, Action.UPDATE);
	}
}
