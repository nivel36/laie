package ged.ejb.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import ged.ejb.core.Repository;
import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.Action;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.user.User;
import ged.ejb.user.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	@Inject
	@Repository
	private PersistenceFacade persistenceFacade;

	@Override
	public long countAdminRoles() {
		final Long count = (Long) this.persistenceFacade.getByQuerySingleResult("User.countAdminRoles", null);
		return count;
	}

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
	public boolean emailExists(final String email) {
		final Map<String, Object> parameters = new HashMap<>(1);
		parameters.put("email", email);
		final Long emails = (Long) this.persistenceFacade.getByQuerySingleResult("User.countEmail", parameters);
		return emails > 0;
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
		return this.persistenceFacade.getByTypedQuery(User.class, "User.findAll", null, 0, 0);
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
	public List<User> findUserTeam(final Long id) {
		final Map<String, Object> parameters = new HashMap<>(2);
		parameters.put("id", id);
		return this.persistenceFacade.getByTypedQuery(User.class, "User.findUserTeam", parameters, 0, 0);
	}

	@Override
	public List<User> fullSearch(final String matching) {
		return this.persistenceFacade.fullSearch(User.class, matching, "name", "surename", "email");
	}

	@Override
	public List<User> fullSearch(final String name, final String surename) {
		final List<String> fields = new ArrayList<>();
		fields.add("name");
		fields.add("surename");
		final List<String> values = new ArrayList<>();
		values.add(name);
		values.add(surename);
		return this.persistenceFacade.fullSearch(User.class, fields, values);
	}

	@Override
	public List<User> fullSearchByName(final String name) {
		return this.persistenceFacade.fullSearch(User.class, name, "name");
	}

	@Override
	public List<User> fullSearchBySurename(final String surename) {
		return this.persistenceFacade.fullSearch(User.class, surename, "surename");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> fullSearchManager(final String name, final String surename, final String email) {
		final EntityManager em = this.persistenceFacade.getEm();
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(User.class)
				.get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		if (name != null) {
			bj.must(qb.keyword().onField("name").matching(name).createQuery());
		}
		if (surename != null) {
			bj.must(qb.keyword().onField("surename").matching(surename).createQuery());
		}
		bj.must(qb.keyword().onField("email").matching(email).createQuery()).not();
		final Query persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), User.class);
		final List<User> result = persistenceQuery.getResultList();
		return result;
	}

	@Override
	public void insertAction(final Action action) {
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
