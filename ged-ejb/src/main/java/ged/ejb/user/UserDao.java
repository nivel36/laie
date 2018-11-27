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

import ged.ejb.candidate.Origin;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.core.util.Parameters;

@Repository
public class UserDao extends AbstractDao<User> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private void deleteUserClosures(final User user) {
		logger.trace("Delete user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = this.findAntecessorsUserClosures(user);
		for (final UserClosure userClosure : userClosures) {
			this.getPersistenceFacade().delete(UserClosure.class, userClosure);
		}
	}

	public List<Origin> findAllOrigins() {
		return this.getPersistenceFacade().findAll(Origin.class, Page.ALL);
	}

	private List<UserClosure> findAntecessorsUserClosures(final User user) {
		return this.findByQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById", map("id", user.getId()), Page.ALL);
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		try {
			return this.findByQuery(User.class, "User.findSubordinateUsers", map("id", user.getId()), Page.ALL);
		}
		catch (final NoResultException e) {
			logger.trace("No subordinate users for user {} found", user.getEmail(), e);
			return new ArrayList<>();
		}
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			return this.findByQuery(User.class, "User.findByEmail", map("email", email));
		}
		catch (final NoResultException e) {
			logger.trace("No users with email {} found", email, e);
			return null;
		}
	}

	public List<User> findUsersOffline(final LocalDateTime start, final LocalDateTime end) {
		this.validateDates(start, end);
		return this.findByQuery(User.class, "User.findUsersOffline", this.mapDates(start, end), Page.ALL);
	}

	public List<User> findUsersOnline(final LocalDateTime start, final LocalDateTime end) {
		this.validateDates(start, end);
		return this.findByQuery(User.class, "User.findUsersOnline", this.mapDates(start, end), Page.ALL);
	}

	@Override
	public Class<User> getType() {
		return User.class;
	}

	private void insertUserClosure(final User antecessor, final User descendant, final int pathLength) {
		logger.trace("Insert in user closure table. Antecessor {}, descendant {}, pathLength {}", antecessor.getEmail(), descendant.getEmail(), pathLength);
		final UserClosure newUserClosure = new UserClosure(antecessor, descendant, pathLength);
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

	private boolean isDuplicateEmail(final User user) {
		Objects.requireNonNull(user);
		final User repositoryUser = this.findUserByEmail(user.getEmail());
		return (repositoryUser != null) && !(repositoryUser.getId() == user.getId());
	}

	public boolean isEmailInUse(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Boolean.class, "User.emailExists", map("email", email));
	}

	private boolean isRemovingManager(final User user, final User userInDatabase) {
		return (userInDatabase.getManager() != null) && (user.getManager() == null);
	}

	private Parameters mapDates(final LocalDateTime start, final LocalDateTime end) {
		return map("start", start).and("end", end);
	}

	@Override
	protected void postInsert(final User user) {
		if (user.getManager() != null) {
			this.insertUserClosures(user);
		}
	}

	@Override
	protected void postUpdate(final User user) {
		this.updateUserClosures(user);
	}

	@Override
	protected void preInsert(final User user) {
		if (isEmailInUse(user.getEmail())) {
			throw new DuplicateEmailException();
		}
	}

	@Override
	protected void preUpdate(final User user) {
		if (isDuplicateEmail(user)) {
			throw new DuplicateEmailException();
		}
	}

	@Override
	public String[] searchFields() {
		return new String[] { "name", "surname", "email" };
	}

	private void updateUserClosures(final User user) {
		final User userInDatabase = this.find(user.getId());
		if (userInDatabase == null) {
			logger.warn("User doesn't exists");
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

	private void validateDates(final LocalDateTime start, final LocalDateTime end) {
		Objects.requireNonNull(start);
		Objects.requireNonNull(end);
		if (start.isAfter(end)) {
			logger.warn("Start date {} is after end date {}", start, end);
			throw new IllegalStateException("start date is after end");
		}
	}
}