package ged.ejb.core.user;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.Action;
import ged.ejb.core.model.GenericDao;
import ged.ejb.core.model.IllegalUserAction;

@Stateless
public class UserDao {

	@Inject
	private GenericDao genericDao;

	public void insertBookmark(Bookmark bookmark) throws IllegalUserAction {
		Bookmark b = findBookmark(bookmark.getAuditedId(),
				bookmark.getEntity(), bookmark.getUser());
		if (b != null) {
			throw new IllegalUserAction("Bookmark alredy exists");
		}
		genericDao.insert(bookmark);
	}

	public void deleteBookmark(Bookmark bookmark) {
		genericDao.delete(bookmark);
	}

	public void deleteAction(Action action) {
		genericDao.delete(action);
	}

	public Bookmark findBookmark(Long auditedId, String entity, User user) {
		Map<String, Object> parameters = makeParameters(auditedId, entity, user);
		Bookmark b = genericDao.getByTypedQuerySingleResult(Bookmark.class,
				"Bookmark.findByValues", parameters);
		return b;
	}

	public void insertAction(Action action) throws IllegalUserAction {
		Action a = findAction(action.getAuditedId(), action.getEntity(),
				action.getUser());
		if (a != null) {
			throw new IllegalUserAction("Action alredy exist");
		}
		genericDao.insert(action);
	}

	public Action findAction(Long auditedId, String entity, User user) {
		Map<String, Object> parameters = makeParameters(auditedId, entity, user);
		Action a = genericDao.getByTypedQuerySingleResult(Action.class,
				"Action.findByValues", parameters);
		return a;
	}

	private Map<String, Object> makeParameters(Long auditedId, String entity,
			User user) {
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("auditedId", auditedId);
		parameters.put("entity", entity);
		parameters.put("user", user);
		return parameters;
	}
}
