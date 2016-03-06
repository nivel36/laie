package ged.ejb.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ged.ejb.core.Repository;
import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.Action;
import ged.ejb.core.model.IllegalUserAction;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.user.User;
import ged.ejb.user.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public void deleteAction(final Action action) {
		this.persistenceFacade.delete(action);
	}

	@Override
	public void deleteBookmark(final Bookmark bookmark) {
		this.persistenceFacade.delete(bookmark);
	}

	@Override
	public void deleteUser(final User user) {
		this.persistenceFacade.delete(user);
	}

	@Override
	public Action findAction(final Long auditedId, final String entity, final User user) {
		final Map<String, Object> parameters = makeParameters(auditedId, entity, user);
		final Action a = this.persistenceFacade.getByTypedQuerySingleResult(Action.class, "Action.findByValues",
				parameters);
		return a;
	}

	@Override
	public List<User> findAll() {
		return this.persistenceFacade.getAll(User.class);
	}

	@Override
	public Bookmark findBookmark(final Long auditedId, final String entity, final User user) {
		final Map<String, Object> parameters = makeParameters(auditedId, entity, user);
		final Bookmark b = this.persistenceFacade.getByTypedQuerySingleResult(Bookmark.class, "Bookmark.findByValues",
				parameters);
		return b;
	}

	@Override
	public Bookmark findBookmarkByUrl(final String url) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("url", url);
		return this.persistenceFacade.getByTypedQuerySingleResult(Bookmark.class, "Bookmark.findByUrl", parameters);
	}

	@Override
	public User findById(final Long id) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceFacade.getByTypedQuerySingleResult(User.class, "User.findById", parameters);
	}

	@Override
	public User findByName(final String user) {
		return this.persistenceFacade.getByTypedQuerySingleResult(User.class, "User.findByName", null);
	}

	@Override
	public User findUserByUsername(final String username) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", username);
		return this.persistenceFacade.getByTypedQuerySingleResult(User.class, "User.findByUsername", parameters);
	}

	@Override
	public List<User> findUsers(final String name, final String surename) {
		final Map<String, Object> parameters = new HashMap<>(2);
		parameters.put("name", name);
		parameters.put("surename", surename);
		return this.persistenceFacade.getByTypedQuery(User.class, "User.findByNameAndSurename", parameters, 0, 0);
	}

	@Override
	public void insertAction(final Action action) throws IllegalUserAction {
		final Action a = findAction(action.getAuditedId(), action.getEntity(), action.getUser());
		if (a != null) {
			throw new IllegalUserAction("Action alredy exist");
		}
		this.persistenceFacade.insert(action);
	}

	@Override
	public void insertBookmark(final Bookmark bookmark) {
		this.persistenceFacade.insert(bookmark);
	}

	@Override
	public void insertUser(final User user) {
		this.persistenceFacade.insert(user);
	}

	private Map<String, Object> makeParameters(final Long auditedId, final String entity, final User user) {
		final Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("auditedId", auditedId);
		parameters.put("entity", entity);
		parameters.put("user", user);
		return parameters;
	}

	@Override
	public User updateUser(final User user) {
		return this.persistenceFacade.update(user);
	}
}
