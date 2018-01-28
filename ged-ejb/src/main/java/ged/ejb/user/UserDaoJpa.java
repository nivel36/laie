package ged.ejb.user;

import static ged.ejb.core.model.QueryParameter.with;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
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

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;

@Repository
public final class UserDaoJpa extends AbstractDaoJpa<User> implements UserDao {

	private static final String END = "end";

	private static final String START = "start";

	private static final String EMAIL = "email";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	public UserDaoJpa(final EntityManager entityManager) {
		super(entityManager);
	}

	private void deleteUserClosures(final User user) {
		logger.trace("Delete user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = findAntecessorsUserClosures(user);
		for (final UserClosure userClosure : userClosures) {
			getEm().remove(userClosure);
		}
	}

	@Override
	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return (boolean) findByQuery("User.emailExists", with(EMAIL, email).parameters());
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
		final List<User> users;
		try {
			users = findByTypedQuery(User.class, "User.findSubordinateUsers", with("id", user.getId()).parameters(), 0,
					0);
		} catch (final NoResultException e) {
			return new ArrayList<>();
		}
		return users;
	}

	@Override
	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		final User user;
		try {
			user = findByTypedQuery(User.class, "User.findByEmail", with(EMAIL, email).parameters());
		} catch (final NoResultException e) {
			return null;
		}
		return user;
	}

	@Override
	public List<User> findUsersOffline(final Date start, final Date end) {
		return findByTypedQuery(User.class, "User.findUsersOffline", with(START, start).and(END, end).parameters(),
				0, 0);
	}

	@Override
	public List<User> findUsersOnline(final Date start, final Date end) {
		return findByTypedQuery(User.class, "User.findUsersOnline", with(START, start).and(END, end).parameters(),
				0, 0);
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
		logger.trace("Insert in user closure table. Antecessor {}, descendant {}, pathLength {}", antecessor,
				descendant, pathLength);
		final UserClosure newUserClosure = new UserClosure();
		newUserClosure.setAntecessor(antecessor);
		newUserClosure.setDescendant(descendant);
		newUserClosure.setPathLength(pathLength);
		getEm().persist(newUserClosure);
	}

	private void insertUserClosures(final User user) {
		logger.trace("Insert user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = findAntecessorsUserClosures(user.getManager());
		for (final UserClosure userClosure : userClosures) {
			insertUserClosure(userClosure.getAntecessor(), user, userClosure.getPathLength() + 1);
		}
		insertUserClosure(user, user, 0);
	}

	@Override
	public long numberOfUsersOffline(final Date start, final Date end) {
		return findByTypedQuery(Long.class, "User.numberOfUsersOffline",
				with(START, start).and(END, end).parameters());
	}

	@Override
	public long numberOfUsersOnline(final Date start, final Date end) {
		return findByTypedQuery(Long.class, "User.numberOfUsersOnline",
				with(START, start).and(END, end).parameters());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> search(final String query, final boolean showDeleted) {
		Objects.requireNonNull(query);
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(User.class)
				.get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		bj.should(qb.keyword().onField("name").matching(query).createQuery());
		bj.should(qb.keyword().onField("surname").matching(query).createQuery());
		bj.should(qb.keyword().onField(EMAIL).matching(query).createQuery());
		bj.must(qb.keyword().onField("deleted").matching(true).createQuery()).not();
		final Query persistenceQuery = fullTextEntityManager.createFullTextQuery(bj.createQuery(), User.class);
		return persistenceQuery.getResultList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> searchByNameAndSurname(final String name, final String surname, final boolean showDeleted) {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEm());
		final QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(User.class)
				.get();
		final BooleanJunction<BooleanJunction> bj = qb.bool();
		if (name != null) {
			bj.must(qb.keyword().onField("name").matching(name).createQuery());
		}
		if (surname != null) {
			bj.must(qb.keyword().onField("surname").matching(surname).createQuery());
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
	public User update(final User user) {
		Objects.requireNonNull(user);
		final User userInDatabase = find(user.getId());
		if (userInDatabase.getManager() == null && user.getManager() != null) {
			insertUserClosures(user);
		} else if (userInDatabase.getManager() != null && user.getManager() == null) {
			deleteUserClosures(userInDatabase);
		} else if (userInDatabase.getManager() != null && user.getManager() != null
				&& !user.getManager().equals(userInDatabase.getManager())) {
			deleteUserClosures(userInDatabase);
			insertUserClosures(user);
		}
		return getEm().merge(user);
	}
}