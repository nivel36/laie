package ged.ejb.user;

import static ged.ejb.core.util.Parameters.map;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.core.security.LoginToken.TokenType;

@Repository
public class UserDao extends AbstractIndexedDao<User> {

	private static final String EMAIL = "email";

	private static final String ID = "id";

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private void deleteUserClosures(final User user) {
		logger.trace("Delete user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = this.findAntecessorsUserClosures(user);
		for (final UserClosure userClosure : userClosures) {
			this.getPersistenceFacade().delete(UserClosure.class, userClosure);
		}
	}

	private List<UserClosure> findAntecessorsUserClosures(final User user) {
		return this.findByQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById", map(ID, user.getId()),
				Page.ALL_RESULTS);
	}

	public User findByUid(final String uid) {
		Objects.requireNonNull(uid);
		try {
			return this.findByQuery(User.class, "User.findByUid", map("uid", uid));
		}
		catch(NoResultException e) {
			return null;
		}
	}

	public Credential findCredential(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Credential.class, "User.findCredential", map(EMAIL, email));
	}

	public List<User> findSubordinateUsers(final String email) {
		Objects.requireNonNull(email);
		final User user = this.findUserByEmail(email);
		if (user == null) {
			throw new NullPointerException(String.format("User with email %s doesn't exists", email));
		}
		return this.findByQuery(User.class, "User.findSubordinateUsers", map(ID, user.getId()), Page.ALL_RESULTS);
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			return this.findByQuery(User.class, "User.findByEmail", map(EMAIL, email));
		} catch (final NoResultException e) {
			return null;
		}
	}

	public User findUserByTokenHashAndType(final byte[] tokenHash, final TokenType type) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(type);
		try {
			return this.getPersistenceFacade().findByQuery(User.class, "User.findByTokenHashAndType",
					map("tokenHash", tokenHash).and("type", type));
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public Class<User> getType() {
		return User.class;
	}

	private void insertUserClosure(final User antecessor, final User descendant, final int pathLength) {
		logger.trace("Insert in user closure table. Antecessor {}, descendant {}, pathLength {}", antecessor.getEmail(),
				descendant.getEmail(), pathLength);
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
		return (userInDatabase.getManager() != null) && (user.getManager() != null)
				&& !user.getManager().equals(userInDatabase.getManager());
	}

	private boolean isDuplicateEmail(final User user) {
		Objects.requireNonNull(user);
		final User repositoryUser = this.findUserByEmail(user.getEmail());
		return (repositoryUser != null) && (repositoryUser.getId() != user.getId());
	}

	public boolean isEmailInUse(final String email) {
		Objects.requireNonNull(email);
		return this.findByQuery(Boolean.class, "User.emailExists", map(EMAIL, email));
	}

	private boolean isRemovingManager(final User user, final User userInDatabase) {
		return (userInDatabase.getManager() != null) && (user.getManager() == null);
	}

	public boolean isSubordinateUser(final String managerEmail, final String subordinateEmail) {
		Objects.requireNonNull(managerEmail);
		final User manager = this.findUserByEmail(managerEmail);
		final User subordinate = this.findUserByEmail(subordinateEmail);
		return this.findByQuery(Boolean.class, "User.IsSubordinateUser",
				map("managerId", manager.getId()).and("subordinateId", subordinate.getId()));
	}

	@Override
	protected void postInsert(final User user) {
		if (user.getManager() != null) {
			this.insertUserClosures(user);
		}
	}

	@Override
	protected void preInsert(final User user) {
		if (this.isEmailInUse(user.getEmail())) {
			throw new DuplicateEmailException();
		}
	}

	@Override
	protected void preUpdate(final User user) {
		if (this.isDuplicateEmail(user)) {
			throw new DuplicateEmailException();
		}
		this.updateUserClosures(user);
	}

	@Override
	public String[] searchFields() {
		return new String[] { "_name", "_surname" };
	}

	private void updateUserClosures(final User user) {
		final User userInDatabase = this.find(user.getId());
		if (userInDatabase == null) {
			logger.warn("User doesn't exists");
			throw new IllegalStateException();
		}
		if (this.isAddingManager(user, userInDatabase)) {
			this.insertUserClosures(user);
		} else if (this.isRemovingManager(user, userInDatabase)) {
			this.deleteUserClosures(userInDatabase);
		} else if (this.isChangingManager(user, userInDatabase)) {
			this.deleteUserClosures(userInDatabase);
			this.insertUserClosures(user);
		}
	}
}