package ged.ejb.core.action;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;

@Stateless
public class ActionServiceImpl extends AbstractService<Long, Action> implements ActionService {

	@Inject
	@Repository
	private ActionDao actionDao;

	@Override
	public void addAction(final AuditedEntity auditedEntity, final String actionType) {
		final Action action = getActionFromEntity(auditedEntity);
		action.setActionPerformed(actionType);
		insert(action);
	}

	@Override
	public List<Action> findAllByUser(final User user) {
		return this.actionDao.findAllByUser(user);
	}

	private Action getActionFromEntity(final AuditedEntity auditedEntity) {
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
}
