package ged.ejb.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;

@Repository
public final class UserDaoJpa extends AbstractDao<Long, User> implements UserDao {

	private static final Logger logger = Logger.getLogger(UserDaoJpa.class.getName());

	@Inject
	public UserDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		logger.log(Level.FINE, "Looking  ");
		final Map<String, Object> parameters = new HashMap<>(1);
		parameters.put("email", email);
		return findByTypedQuery(Boolean.class, "User.emailExists", parameters);
	}

	@Override
	public Boolean existsMoreThanOneAdmin() {
		logger.fine("Looking for if exists more than one admin");
		return findByTypedQuery(Boolean.class, "User.existsMoreThanOneAdmin", null);
	}

	@Override
	public List<User> findAll() {
		logger.fine("Finding all users");
		return findByTypedQuery(User.class, "User.findAll", null, 0, 0);
	}

	public List<UserClosure> findAntecessorsUserClosures(final User user) {
		Objects.requireNonNull(user);
		logger.log(Level.FINER, "Finding all antecessors of the user");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", user.getId());
		return findByTypedQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById", parameters, 0, 0);
	}

	@Override
	public List<User> findSubordinateUsers(final Long id) {
		Objects.requireNonNull(id);
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		logger.log(Level.FINE, "Find subordinate users of user with id {}", id);
		final Map<String, Object> parameters = new HashMap<>(1);
		parameters.put("id", id);
		return findByTypedQuery(User.class, "User.findSubordinateUsers", parameters, 0, 0);
	}

	@Override
	public User findUserByUsername(final String username) {
		Objects.requireNonNull(username);
		logger.log(Level.FINE, "Finding user with username {}", username);
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", username);
		return findByTypedQuery(User.class, "User.findByUsername", parameters);
	}

	@Override
	public Class<User> getType() {
		return User.class;
	}

	@Override
	public void insert(final User user) {
		Objects.requireNonNull(user);
		logger.log(Level.FINE, "Insert user {}", user.getFullName());
		getEm().persist(user);
		if (user.getManager() != null) {
			insertUserClosures(user);
		}
	}

	private void insertUserClosure(final User antecessor, final User descendant, final int pathLength) {
		logger.log(Level.FINER, "Insert in user closure table. Antecessor {}, descendant {}, pathLength {}",
				new Object[] { antecessor, descendant, pathLength });
		final UserClosure newUserClosure = new UserClosure();
		newUserClosure.setAntecessor(antecessor);
		newUserClosure.setDescendant(descendant);
		newUserClosure.setPathLength(pathLength);
		getEm().persist(newUserClosure);
	}

	private void insertUserClosures(final User user) {
		logger.log(Level.FINER, "Insert user closures for user {}", user);
		final List<UserClosure> userClosures = findAntecessorsUserClosures(user.getManager());
		for (final UserClosure userClosure : userClosures) {
			insertUserClosure(userClosure.getAntecessor(), user, userClosure.getPathLength() + 1);
		}
		insertUserClosure(user, user, 0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> searchByNameAndSurename(final String name, final String surename, final String email,
			final boolean showDeleted) {
		logger.log(Level.FINE, "SEARCH user by name {} and surename {}, removing users with email {}",
				new Object[] { name, surename, email });
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
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
		final Query persistenceQuery;
		if (bj.isEmpty()) {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(qb.all().createQuery(), User.class);
		} else {
			persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), User.class);
		}
		return persistenceQuery.getResultList();
	}

	@Override
	public Boolean usernameExists(final String username) {
		Objects.requireNonNull(username);
		UserDaoJpa.logger.log(Level.FINE, "Username {} exists?", username);
		final Map<String, Object> parameters = new HashMap<>(1);
		parameters.put("username", username);
		return findByTypedQuery(Boolean.class, "User.usernameExists", parameters);
	}
}