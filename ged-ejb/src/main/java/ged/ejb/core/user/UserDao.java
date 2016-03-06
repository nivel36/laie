package ged.ejb.core.user;

import java.util.HashMap;
import java.util.List;
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

	public void deleteAction(final Action action) {
		this.genericDao.delete(action);
	}

	public void deleteBookmark(final Bookmark bookmark) {
		this.genericDao.delete(bookmark);
	}

	public Action findAction(final Long auditedId, final String entity, final User user) {
		final Map<String, Object> parameters = makeParameters(auditedId, entity, user);
		final Action a = this.genericDao.getByTypedQuerySingleResult(Action.class, "Action.findByValues", parameters);
		return a;
	}

	public List<User> findAll() {
		return this.genericDao.getAll(User.class);
	}

	public Bookmark findBookmark(final Long auditedId, final String entity, final User user) {
		final Map<String, Object> parameters = makeParameters(auditedId, entity, user);
		final Bookmark b = this.genericDao.getByTypedQuerySingleResult(Bookmark.class, "Bookmark.findByValues",
				parameters);
		return b;
	}

	public Bookmark findBookmarkByUrl(final String url) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("url", url);
		return this.genericDao.getByTypedQuerySingleResult(Bookmark.class, "Bookmark.findByUrl", parameters);
	}

	public User findById(final Long id) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.genericDao.getByTypedQuerySingleResult(User.class, "User.findById", parameters);
	}

	public User findByName(final String user) {
		return this.genericDao.getByTypedQuerySingleResult(User.class, "User.findByName");
	}

	public List<User> findUsers(final String name, final String surename) {
		final Map<String, Object> parameters = new HashMap<>(2);
		parameters.put("name", name);
		parameters.put("surename", surename);
		return this.genericDao.getByTypedQuery(User.class, "User.findByNameAndSurename", parameters);
	}

	public void insertAction(final Action action) throws IllegalUserAction {
		final Action a = findAction(action.getAuditedId(), action.getEntity(), action.getUser());
		if (a != null) {
			throw new IllegalUserAction("Action alredy exist");
		}
		this.genericDao.insert(action);
	}

	public void insertBookmark(final Bookmark bookmark) {
		// final Bookmark b = findBookmark(bookmark.getAuditedId(),
		// bookmark.getEntity(), bookmark.getUser());
		// if (b != null) {
		// throw new IllegalUserAction("Bookmark alredy exists");
		// }
		this.genericDao.insert(bookmark);
	}

	public void insertUser(final User user) {
		this.genericDao.insert(user);
	}

	private Map<String, Object> makeParameters(final Long auditedId, final String entity, final User user) {
		final Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("auditedId", auditedId);
		parameters.put("entity", entity);
		parameters.put("user", user);
		return parameters;
	}

	public void removeBookmark(final Bookmark bookmark) {
		this.genericDao.delete(bookmark);
	}

	public User updateUser(final User user) {
		return this.genericDao.update(user);
	}
}
