package ged.ejb.user;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.util.Parameters;

@Repository
public final class UserDao extends AbstractDao<User> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private void checkDates(final LocalDateTime start, final LocalDateTime end) {
		Objects.requireNonNull(start);
		Objects.requireNonNull(end);
		if (start.compareTo(end) > 0) {
			logger.error("Start date {} after end date {}", start, end);
			throw new IllegalStateException("start after end");
		}
	}

	private void deleteUserClosures(final User user) {
		logger.trace("Delete user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = this.findAntecessorsUserClosures(user);
		for (final UserClosure userClosure : userClosures) {
			this.getPersistenceFacade().delete(UserClosure.class, userClosure);
		}
	}

	public boolean existsMoreThanOneAdmin() {
		return this.findByQuery(Boolean.class, "User.existsMoreThanOneAdmin");
	}

	@Override
	public List<User> findAll() {
		return this.findByQuery(User.class, "User.findAll", 0, 0);
	}

	private List<UserClosure> findAntecessorsUserClosures(final User user) {
		Objects.requireNonNull(user);
		return this.findByQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById", map("id", user.getId()), 0, 0);
	}

	public List<User> findSubordinateUsers(final User user) {
		try {
			Objects.requireNonNull(user);
			return this.findByQuery(User.class, "User.findSubordinateUsers", map("id", user.getId()), 0, 0);
		}
		catch (final NoResultException e) {
			logger.debug("No subordinate users found", e);
			return new ArrayList<>();
		}
	}

	public User findUserByEmail(final String email) {
		try {
			Objects.requireNonNull(email);
			return this.findByQuery(User.class, "User.findByEmail", map("email", email));
		}
		catch (final NoResultException e) {
			logger.debug("No users with email {} found", email, e);
			return null;
		}
	}

	public List<User> findUsersOffline(final LocalDateTime start, final LocalDateTime end) {
		this.checkDates(start, end);
		return this.findByQuery(User.class, "User.findUsersOffline", this.mapDates(start, end), 0, 0);
	}

	public List<User> findUsersOnline(final LocalDateTime start, final LocalDateTime end) {
		this.checkDates(start, end);
		return this.findByQuery(User.class, "User.findUsersOnline", this.mapDates(start, end), 0, 0);
	}

	@Override
	public Class<User> getType() {
		return User.class;
	}

	private void insertUserClosure(final User antecessor, final User descendant, final int pathLength) {
		logger.trace("Insert in user closure table. Antecessor {}, descendant {}, pathLength {}", antecessor, descendant, pathLength);
		final UserClosure newUserClosure = new UserClosure();
		newUserClosure.setAntecessor(antecessor);
		newUserClosure.setDescendant(descendant);
		newUserClosure.setPathLength(pathLength);
		this.getPersistenceFacade().insert(newUserClosure);
	}

	private void insertUserClosures(final User user) {
		logger.trace("Insert user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = this.findAntecessorsUserClosures(user.getManager());
		for (final UserClosure userClosure : userClosures) {
			this.insertUserClosure(userClosure.getAntecessor(), user, userClosure.getPathLength() + 1);
		}
		this.insertUserClosure(user, user, 0);
	}

	private boolean isAddingManager(final User user, final User userInDatabase) {
		return (userInDatabase.getManager() == null) && (user.getManager() != null);
	}

	private boolean isChangingManager(final User user, final User userInDatabase) {
		return (userInDatabase.getManager() != null) && (user.getManager() != null) && !user.getManager().equals(userInDatabase.getManager());
	}

	public boolean isDuplicatedEmail(final String email) {
		return this.findByQuery(Boolean.class, "User.emailExists", map("email", email));
	}

	private boolean isRemovingManager(final User user, final User userInDatabase) {
		return (userInDatabase.getManager() != null) && (user.getManager() == null);
	}

	private Parameters mapDates(final LocalDateTime start, final LocalDateTime end) {
		return map("start", start).and("end", end);
	}

	public long numberOfUsersInTeam(final User user) {
		Objects.requireNonNull(user);
		return this.findByQuery(Long.class, "User.numberOfUsersInTeam", map("id", user.getId()));
	}

	public long numberOfUsersOffline(final LocalDateTime start, final LocalDateTime end) {
		this.checkDates(start, end);
		return this.findByQuery(Long.class, "User.numberOfUsersOffline", this.mapDates(start, end));
	}

	public long numberOfUsersOnline(final LocalDateTime start, final LocalDateTime end) {
		this.checkDates(start, end);
		return this.findByQuery(Long.class, "User.numberOfUsersOnline", this.mapDates(start, end));
	}

	@Override
	public User save(final User user) {
		if (user.getId() == 0) {
			this.getPersistenceFacade().insert(user);
			if (user.getManager() != null) {
				this.insertUserClosures(user);
			}
		}
		else {
			Objects.requireNonNull(user);
			final User userInDatabase = this.find(user.getId());
			if (userInDatabase == null) {
				logger.error("User don't exists");
				throw new IllegalStateException();
			}
			if (this.isAddingManager(user, userInDatabase)) {
				this.insertUserClosures(user);
			}
			else if (this.isRemovingManager(user, userInDatabase)) {
				this.deleteUserClosures(userInDatabase);
			}
			else if (this.isChangingManager(user, userInDatabase)) {
				this.deleteUserClosures(userInDatabase);
				this.insertUserClosures(user);
			}
		}
		return this.getPersistenceFacade().update(user);
	}

	@Override
	public List<User> search(final String searchText) {
		return this.getPersistenceFacade().search(User.class, searchText, "name", "surname", "email");
	}
}