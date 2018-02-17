package ged.ejb.user;

import static ged.ejb.core.model.FluentHashMap.map;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.FluentHashMap;
import ged.ejb.core.model.Repository;

@Repository
public final class UserDaoJpa extends AbstractDaoJpa<User> implements UserDao {

	private static final String EMAIL = "email";

	private static final String END = "end";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final String NAME = "name";

	private static final String START = "start";

	private static final String SURNAME = "surname";

	private void deleteUserClosures(final User user) {
		logger.trace("Delete user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = findAntecessorsUserClosures(user);
		for (final UserClosure userClosure : userClosures) {
			getPersistenceFacade().delete(UserClosure.class, userClosure);
		}
	}

	@Override
	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return (boolean) this.findByQuery("User.emailExists", map(EMAIL, email));
	}

	@Override
	public boolean existsMoreThanOneAdmin() {
		return (boolean) this.findByQuery("User.existsMoreThanOneAdmin");
	}

	@Override
	public List<User> findAll() {
		return this.findByQuery(User.class, "User.findAll", 0, 0);
	}

	private List<UserClosure> findAntecessorsUserClosures(final User user) {
		Objects.requireNonNull(user);
		return this.findByQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById",
				map("id", user.getId()), 0, 0);
	}

	@Override
	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		final List<User> users;
		try {
			users = this.findByQuery(User.class, "User.findSubordinateUsers", map("id", user.getId()), 0, 0);
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
			user = this.findByQuery(User.class, "User.findByEmail", map(EMAIL, email));
		} catch (final NoResultException e) {
			return null;
		}
		return user;
	}

	@Override
	public List<User> findUsersOffline(final Date start, final Date end) {
		map(START, start);
		return this.findByQuery(User.class, "User.findUsersOffline", FluentHashMap.map(END, end), 0, 0);
	}

	@Override
	public List<User> findUsersOnline(final Date start, final Date end) {
		map(START, start);
		return this.findByQuery(User.class, "User.findUsersOnline", FluentHashMap.map(END, end), 0, 0);
	}

	@Override
	public Class<User> getType() {
		return User.class;
	}

	@Override
	public void insert(final User user) {
		Objects.requireNonNull(user);
		getPersistenceFacade().insert(user);
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
		getPersistenceFacade().insert(newUserClosure);
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
	public long numberOfUsersInTeam(final User user) {
		return this.findByQuery(Long.class, "User.numberOfUsersInTeam", map("id", user.getId()));
	}

	@Override
	public long numberOfUsersOffline(final Date start, final Date end) {
		map(START, start);
		return this.findByQuery(Long.class, "User.numberOfUsersOffline", FluentHashMap.map(END, end));
	}

	@Override
	public long numberOfUsersOnline(final Date start, final Date end) {
		map(START, start);
		return this.findByQuery(Long.class, "User.numberOfUsersOnline", FluentHashMap.map(END, end));
	}

	@Override
	public List<User> search(final String searchText) {
		return getPersistenceFacade().search(User.class, searchText, NAME, SURNAME, EMAIL);
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
		return getPersistenceFacade().update(user);
	}
}