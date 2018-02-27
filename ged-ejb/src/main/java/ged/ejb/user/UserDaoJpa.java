package ged.ejb.user;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDaoJpa;
import ged.ejb.core.model.Repository;
import ged.ejb.core.util.Parameters;

@Repository
public final class UserDaoJpa extends AbstractDaoJpa<User> implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private void checkDates(final LocalDate start, final LocalDate end) {
		Objects.requireNonNull(start);
		Objects.requireNonNull(end);
		if (start.compareTo(end) > 0) {
			throw new IllegalStateException("start after end");
		}
	}

	private void deleteUserClosures(final User user) {
		logger.trace("Delete user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = findAntecessorsUserClosures(user);
		for (final UserClosure userClosure : userClosures) {
			getPersistenceFacade().delete(UserClosure.class, userClosure);
		}
	}

	@Override
	public boolean emailExist(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Boolean.class, "User.emailExists", map("email", email));
	}

	@Override
	public boolean existMoreThanOneAdmin() {
		return this.findByQuery(Boolean.class, "User.existsMoreThanOneAdmin");
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
		try {
			return this.findByQuery(User.class, "User.findSubordinateUsers", map("id", user.getId()), 0, 0);
		} catch (final NoResultException e) {
			return new ArrayList<>();
		}
	}

	@Override
	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			return this.findByQuery(User.class, "User.findByEmail", map("email", email));
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public List<User> findUsersOffline(final LocalDate start, final LocalDate end) {
		checkDates(start, end);
		return this.findByQuery(User.class, "User.findUsersOffline", mapDates(start, end), 0, 0);
	}

	@Override
	public List<User> findUsersOnline(final LocalDate start, final LocalDate end) {
		checkDates(start, end);
		return this.findByQuery(User.class, "User.findUsersOnline", mapDates(start, end), 0, 0);
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

	private boolean isAddingManager(final User user, final User userInDatabase) {
		return userInDatabase.getManager() == null && user.getManager() != null;
	}

	private boolean isChangingManager(final User user, final User userInDatabase) {
		return userInDatabase.getManager() != null && user.getManager() != null
				&& !user.getManager().equals(userInDatabase.getManager());
	}

	private boolean isRemovingManager(final User user, final User userInDatabase) {
		return userInDatabase.getManager() != null && user.getManager() == null;
	}

	private Parameters mapDates(final LocalDate start, final LocalDate end) {
		return map("start", start).and("end", end);
	}

	@Override
	public long numberOfUsersInTeam(final User user) {
		Objects.requireNonNull(user);
		return this.findByQuery(Long.class, "User.numberOfUsersInTeam", map("id", user.getId()));
	}

	@Override
	public long numberOfUsersOffline(final LocalDate start, final LocalDate end) {
		checkDates(start, end);
		return this.findByQuery(Long.class, "User.numberOfUsersOffline", mapDates(start, end));
	}

	@Override
	public long numberOfUsersOnline(final LocalDate start, final LocalDate end) {
		checkDates(start, end);
		return this.findByQuery(Long.class, "User.numberOfUsersOnline", mapDates(start, end));
	}

	@Override
	public List<User> search(final String searchText) {
		return getPersistenceFacade().search(User.class, searchText, "name", "surname", "email");
	}

	@Override
	public User update(final User user) {
		Objects.requireNonNull(user);
		final User userInDatabase = find(user.getId());
		if (isAddingManager(user, userInDatabase)) {
			insertUserClosures(user);
		} else if (isRemovingManager(user, userInDatabase)) {
			deleteUserClosures(userInDatabase);
		} else if (isChangingManager(user, userInDatabase)) {
			deleteUserClosures(userInDatabase);
			insertUserClosures(user);
		}
		return getPersistenceFacade().update(user);
	}
}