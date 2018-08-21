package ged.ejb.core.action;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.events.PostDelete;
import ged.ejb.core.events.PostLogin;
import ged.ejb.core.events.PostPersist;
import ged.ejb.core.events.PostUndelete;
import ged.ejb.core.events.PostUpdate;
import ged.ejb.core.model.AbstractAuditedEntity;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Stateless
public class ActionService extends AbstractService<Action> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private ActionDao actionDao;

	@Resource
	private SessionContext sessionContext;

	@Inject
	private UserService userService;

	public void deleteAction(@PostDelete @Observes final AbstractAuditedEntity auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Delete action class {} with id {} for user {}", auditedEntity.getClass().getName(), auditedEntity.getId(), auditedEntity.getUser());
		this.insertAction(auditedEntity, ActionType.DELETE);
	}

	public List<Action> findAllByUser(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Find all actions of the user {}", user.getFullName());
		return this.actionDao.findAllByUser(user);
	}

	public List<Action> findLastActions() {
		logger.debug("Find last actions");
		return this.actionDao.findLastActions();
	}

	private Action getActionFromEntity(final AbstractAuditedEntity auditedEntity) {
		final Action action = new Action();
		action.setEntityId(auditedEntity.getId());
		action.setEntityClass(auditedEntity.getClass().getSimpleName());
		action.setText(auditedEntity.toString());
		return action;
	}

	@Override
	protected ActionDao getDao() {
		return this.actionDao;
	}

	public void insertAction(@PostPersist @Observes final AbstractAuditedEntity auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Insert action class {} with id {} for user {}", auditedEntity.getClass().getName(), auditedEntity.getId(), auditedEntity.getUser());
		this.insertAction(auditedEntity, ActionType.SAVE);
	}

	private void insertAction(final AbstractAuditedEntity auditedEntity, final ActionType actionType) {
		final Action action = this.getActionFromEntity(auditedEntity);
		action.setActionPerformed(actionType.name());
		action.setDate(LocalDateTime.now());
		final User user = this.userService.findUserByEmail(this.sessionContext.getCallerPrincipal().getName());
		action.setUser(user);
		this.save(action);
	}

	public void loginAction(@PostLogin @Observes final String email) {
		Objects.requireNonNull(email);
		logger.debug("Login user {}", email);
		final User user = this.userService.findUserByEmail(email);
		this.insertAction(user, ActionType.LOGIN);
	}

	public void setActionDao(final ActionDao actionDao) {
		this.actionDao = actionDao;
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	public void undeleteAction(@PostUndelete @Observes final AbstractAuditedEntity auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Undelete action class {} with id {} for user {}", auditedEntity.getClass().getName(), auditedEntity.getId(), auditedEntity.getUser());
		this.insertAction(auditedEntity, ActionType.UNDELETE);
	}

	public void updateAction(@PostUpdate @Observes final AbstractAuditedEntity auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Update action class {} with id {} for user {}", auditedEntity.getClass().getName(), auditedEntity.getId(), auditedEntity.getUser());
		this.insertAction(auditedEntity, ActionType.SAVE);
	}
}
