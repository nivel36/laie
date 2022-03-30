package es.nivel36.laie.ejb.user;

import static es.nivel36.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.core.model.AbstractDao;
import es.nivel36.core.model.Page;
import es.nivel36.core.model.Repository;
import es.nivel36.core.model.search.SearchFacets;
import es.nivel36.core.model.search.SearchResult;
import es.nivel36.core.model.search.SortField;
import es.nivel36.core.util.Parameters;
import es.nivel36.laie.ejb.core.security.LoginToken.TokenType;

@Repository
public class UserDao extends AbstractDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	public void insert(final User user) {
		setUid(User.class, user);
		this.em.persist(user);
		if (user.getManager() != null) {
			this.insertUserClosures(user);
		}
	}

	public void update(final User user) {
		this.updateUserClosures(user);
	}

	public boolean checkDuplicateEmail(final String email) {
		Objects.requireNonNull(email);
		return this.checkDuplicateField(User.class, "email", email);
	}

	public User findUserByUid(final String uid) {
		Objects.requireNonNull(uid);
		final String namedQuery = "User.findByUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(User.class, namedQuery, parameters);
	}

	public Credential findCredential(final String email) {
		Objects.requireNonNull(email);
		final String namedQuery = "User.findCredential";
		final Parameters parameters = map("email", email);
		return this.findByQuery(Credential.class, namedQuery, parameters);
	}

	public List<User> findSubordinateUsers(final String userUid) {
		Objects.requireNonNull(userUid);
		final User user = this.findUserByUid(userUid);
		final String namedQuery = "User.findSubordinateUsers";
		Parameters parameters = map("id", user.getId());
		return this.findByQuery(User.class, namedQuery, parameters, Page.ALL_RESULTS);
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		final String namedQuery = "User.findByEmail";
		final Parameters parameters = map("email", email);
		return this.findByQuery(User.class, namedQuery, parameters);
	}

	public User findUserByTokenHashAndType(final byte[] tokenHash, final TokenType type) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(type);
		final String namedQuery = "User.findByTokenHashAndType";
		final Parameters parameters = map("tokenHash", tokenHash).and("type", type);
		return this.findByQuery(User.class, namedQuery, parameters);
	}

	public boolean isSubordinateUser(final User user, final User subordinate) {
		long userId = user.getId();
		long subordinateId = subordinate.getId();
		final String namedQuery = "User.isSubordinateUser";
		final Parameters parameters = map("managerId", userId).and("subordinateId", subordinateId);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public SearchResult<User> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "_name", "_surname", "_email" };
		return this.search(User.class, page, sortOrder, searchFacets, searchText, searchFields);
	}

	///////////////////////////////////////////////////////////////////////////
	// USER CLOSURES
	///////////////////////////////////////////////////////////////////////////

	private List<UserClosure> findAntecessorsUserClosures(final User user) {
		return this.findByQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosuresById",
				map("id", user.getId()), Page.ALL_RESULTS);
	}

	private void deleteUserClosures(final User user) {
		logger.trace("Delete user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = this.findAntecessorsUserClosures(user);
		for (final UserClosure userClosure : userClosures) {
			this.delete(UserClosure.class, userClosure);
		}
	}

	private void updateUserClosures(final User user) {
		final User userInDatabase = this.em.find(User.class, user.getId());
		if (userInDatabase == null) {
			logger.warn("User doesn't exists");
			throw new IllegalStateException();
		}
		final User newManager = user.getManager();
		final User oldManager = userInDatabase.getManager();
		if (this.isAddingManager(newManager, oldManager)) {
			this.insertUserClosures(user);
		} else if (this.isRemovingManager(newManager, oldManager)) {
			this.deleteUserClosures(userInDatabase);
		} else if (this.isChangingManager(newManager, oldManager)) {
			this.deleteUserClosures(userInDatabase);
			this.insertUserClosures(user);
		}
	}

	private boolean isRemovingManager(final User newManager, final User oldManager) {
		return oldManager != null && newManager == null;
	}

	private boolean isAddingManager(final User newManager, final User oldManager) {
		return oldManager == null && newManager != null;
	}

	private boolean isChangingManager(final User newManager, final User oldManager) {
		return oldManager != null && newManager != null && !newManager.equals(oldManager);
	}

	private void insertUserClosures(final User user) {
		logger.trace("Insert user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = this.findAntecessorsUserClosures(user.getManager());
		for (final UserClosure userClosure : userClosures) {
			this.insertUserClosure(userClosure.getAntecessor(), user, userClosure.getPathLength() + 1);
		}
		this.insertUserClosure(user, user, 0);
	}

	private void insertUserClosure(final User antecessor, final User descendant, final int pathLength) {
		logger.trace("Insert in user closure table. Antecessor {}, descendant {}, pathLength {}", antecessor.getEmail(),
				descendant.getEmail(), pathLength);
		final UserClosure newUserClosure = new UserClosure(antecessor, descendant, pathLength);
		this.em.persist(newUserClosure);
	}
}