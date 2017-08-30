package ged.ejb.user;

import static ged.ejb.core.model.QueryParameter.with;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractJpaDao;
import ged.ejb.core.model.Repository;

@Repository
public final class UserDaoJpa extends AbstractJpaDao<User> implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	public UserDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return (boolean) findByQuery("User.emailExists", with("email", email).parameters());
	}

	@Override
	public boolean existsMoreThanOneAdmin() {
		return (boolean) findByQuery("User.existsMoreThanOneAdmin");
	}

	@Override
	public List<User> findAll() {
		return findByTypedQuery(User.class, "User.findAll", 0, 0);
	}

	private List<UserClosure> findAntecessorsUserClosures(final User user) {
		Objects.requireNonNull(user);
		return findByTypedQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById",
				with("id", user.getId()).parameters(), 0, 0);
	}

	@Override
	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		List<User> users;
		try {
			users = findByTypedQuery(User.class, "User.findSubordinateUsers", with("id", user.getId()).parameters(), 0,
					0);
		} catch (NoResultException e) {
			logger.warn("No subordinate Users for user {}", user.getUsername());
			return new ArrayList<>();
		}
		return users;
	}

	@Override
	public User findUserByUsername(final String username) {
		Objects.requireNonNull(username);
		final User user;
		try {
			user = findByTypedQuery(User.class, "User.findByUsername", with("username", username).parameters());
		} catch (final NoResultException e) {
			logger.debug("No user with username {} found", username);
			return null;
		}
		return user;
	}

	@Override
	public Class<User> getType() {
		return User.class;
	}

	@Override
	public void insert(final User user) {
		Objects.requireNonNull(user);
		getEm().persist(user);
		if (user.getManager() != null) {
			insertUserClosures(user);
		}
	}

	private void insertUserClosure(final User antecessor, final User descendant, final int pathLength) {
		logger.trace("Insert in user closure table. Antecessor {}, descendant {}, pathLength {}",
				new Object[] { antecessor, descendant, pathLength });
		final UserClosure newUserClosure = new UserClosure();
		newUserClosure.setAntecessor(antecessor);
		newUserClosure.setDescendant(descendant);
		newUserClosure.setPathLength(pathLength);
		getEm().persist(newUserClosure);
	}

	private void insertUserClosures(final User user) {
		logger.trace("Insert user closures for user {}", user);
		final List<UserClosure> userClosures = findAntecessorsUserClosures(user.getManager());
		for (final UserClosure userClosure : userClosures) {
			insertUserClosure(userClosure.getAntecessor(), user, userClosure.getPathLength() + 1);
		}
		insertUserClosure(user, user, 0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> searchByNameAndSurename(final String name, final String surename, final boolean showDeleted) {
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
	public boolean usernameExists(final String username) {
		Objects.requireNonNull(username);
		return (boolean) findByQuery("User.usernameExists", with("username", username).parameters());
	}
}