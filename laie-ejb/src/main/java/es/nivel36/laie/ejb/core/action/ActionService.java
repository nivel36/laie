package es.nivel36.laie.ejb.core.action;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@Stateless
public class ActionService {

	private static final Logger logger = LoggerFactory.getLogger(ActionService.class);

	private @Inject ActionDao actionDao;
	private @Resource SessionContext sessionContext;
	private @Inject UserService userService;

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

	public void deleteAction(@Delete @Observes final Auditable auditedEntity) {
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
		actionDao.insert(action);
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

	public long countAllByUser(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Count all actions by user {}", user);
		return this.actionDao.countAllByUser(user);
	}

	public List<Action> findAll(final Page page) {
		Objects.requireNonNull(page);
		logger.debug("Find all actions");
		return this.actionDao.findAll(page);
	}

	public long countAll() {
		logger.debug("Count all actions");
		return this.actionDao.countAll();
	}

	public void setSessionContext(final SessionContext sessionContext) {
		this.sessionContext = Objects.requireNonNull(sessionContext);
	}

	public void setActionDao(final ActionDao actionDao) {
		this.actionDao = Objects.requireNonNull(actionDao);
	}

	public void setUserService(final UserService userService) {
		this.userService = Objects.requireNonNull(userService);
	}
}
