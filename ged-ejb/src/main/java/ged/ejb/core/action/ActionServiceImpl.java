package ged.ejb.core.action;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
public class ActionServiceImpl extends AbstractService<Action> implements ActionService {

	private static final Logger logger = LoggerFactory.getLogger(ActionServiceImpl.class.getName());

	private final ActionDao actionDao;

	@Inject
	public ActionServiceImpl(@Repository final ActionDao actionDao) {
		Objects.requireNonNull(actionDao);
		this.actionDao = actionDao;
	}

	@Override
	public void deleteAction(@PostDelete @Observes final AuditedEntity auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Delete action class {} with id {} for user {}",
				new Object[] { auditedEntity.getClass().getName(), auditedEntity.getId(), auditedEntity.getUser() });
		insertAction(auditedEntity, Action.DELETE);
	}

	@Override
	public List<Action> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Find all actions of the user {}", user.getFullName());
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

	@Override
	public void insertAction(@PostPersist @Observes final AuditedEntity auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Insert action class {} with id {} for user {}",
				new Object[] { auditedEntity.getClass().getName(), auditedEntity.getId(), auditedEntity.getUser() });
		insertAction(auditedEntity, Action.INSERT);
	}

	private void insertAction(final AuditedEntity auditedEntity, final String actionType) {
		final Action action = getActionFromEntity(auditedEntity);
		action.setActionPerformed(actionType);
		action.setDate(new Date());
		action.setUser(auditedEntity.getUser());
		save(action);
	}

	@Override
	public void undeleteAction(@PostUndelete @Observes final AuditedEntity auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Undelete action class {} with id {} for user {}",
				new Object[] { auditedEntity.getClass().getName(), auditedEntity.getId(), auditedEntity.getUser() });
		insertAction(auditedEntity, Action.UNDELETE);
	}

	@Override
	public void updateAction(@PostUpdate @Observes final AuditedEntity auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Update action class {} with id {} for user {}",
				new Object[] { auditedEntity.getClass().getName(), auditedEntity.getId(), auditedEntity.getUser() });
		insertAction(auditedEntity, Action.UPDATE);
	}
}
