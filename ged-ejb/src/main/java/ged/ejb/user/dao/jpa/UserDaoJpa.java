package ged.ejb.user.dao.jpa;

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
import ged.ejb.core.action.Action;
import ged.ejb.core.model.PersistenceFacade;
import ged.ejb.user.User;
import ged.ejb.user.UserClosure;
import ged.ejb.user.dao.UserDao;

@Repository
public class UserDaoJpa implements UserDao {

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

	public List<UserClosure> findAntecessorsUserClosures(final User user) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", user.getId());
		return this.persistenceFacade.getByTypedQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById",
				parameters, 0, 0);
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
	public List<User> findSubordinateUsers(final Long id) {
		final Map<String, Object> parameters = new HashMap<>(1);
		parameters.put("id", id);
		return this.persistenceFacade.getByTypedQuery(User.class, "User.findSubordinateUsers", parameters, 0, 0);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> fullSearch(final String name, final String surename, final String email,
			final boolean showDeleted) {
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
		if (email != null) {
			bj.must(qb.keyword().onField("email").matching(email).createQuery()).not();
		}
		if (!showDeleted) {
			bj.must(qb.keyword().onField("deleted").matching(true).createQuery()).not();
		}
		Query persistenceQuery = null;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), User.class);
		} else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), User.class);
		}
		final List<User> result = persistenceQuery.getResultList();
		return result;
	}

	@Override
	public void insertAction(final Action action) {
		this.persistenceFacade.insert(action);
	}

	@Override
	public void insertUser(final User user) {
		this.persistenceFacade.insert(user);
		if (user.getManager() != null) {
			insertUserClosures(user);
		}
	}

	private void insertUserClosure(final User antecessor, final User descendant, final int pathLength) {
		final UserClosure newUserClosure = new UserClosure();
		newUserClosure.setAntecessor(antecessor);
		newUserClosure.setDescendant(descendant);
		newUserClosure.setPathLength(pathLength);
		this.persistenceFacade.insert(newUserClosure);
	}

	private void insertUserClosures(final User user) {
		final List<UserClosure> userClosures = findAntecessorsUserClosures(user.getManager());
		for (final UserClosure userClosure : userClosures) {
			insertUserClosure(userClosure.getAntecessor(), user, userClosure.getPathLength() + 1);
		}
		insertUserClosure(user, user, 0);
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
