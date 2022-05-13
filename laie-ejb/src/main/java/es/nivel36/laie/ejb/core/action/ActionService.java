package es.nivel36.laie.ejb.core.action;

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

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

@Stateless
public class ActionService {

	private static final Logger logger = LoggerFactory.getLogger(ActionService.class);

	@Inject
	@Repository
	private ActionDao actionDao;

	@Resource
	private SessionContext sessionContext;

	@Inject
	private UserService userService;

	public void createAction(@Create @Observes final Auditable auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Create action class {} with id {}", auditedEntity.getEntityName(), auditedEntity.getId());
		this.saveData(ActionType.CREATE, auditedEntity);
	}

	public void updateAction(@Update @Observes final Auditable auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Update action class {} with id {}", auditedEntity.getEntityName(), auditedEntity.getId());
		this.saveData(ActionType.UPDATE, auditedEntity);
	}

	public void deleteAction(@Update @Observes final Auditable auditedEntity) {
		Objects.requireNonNull(auditedEntity);
		logger.debug("Delete action class {} with id {}", auditedEntity.getEntityName(), auditedEntity.getId());
		this.saveData(ActionType.DELETE, auditedEntity);
	}
	
	private void saveData(final ActionType actionType, final Auditable auditedEntity) {
		final User user = this.userService.findUserByEmail(loggedUser());
		final Action action = new Action();
		action.setType(actionType);
		action.setDate(LocalDateTime.now());
		action.setEntityName(auditedEntity.getEntityName());
		action.setEntityTitle(auditedEntity.getEntityTitle());
		action.setEntityId(auditedEntity.getId());
		action.setUser(user);
		actionDao.insertAction(action);
	}

	private String loggedUser() {
		return this.sessionContext.getCallerPrincipal().getName();
	}

	public List<Action> findAllByUser(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		logger.debug("Find all actions by user {}", user);
		return this.actionDao.findAllByUser(user, page);
	}

	public List<Action> findLastActions() {
		logger.debug("Find last actions");
		return this.actionDao.findLastActions();
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
}
