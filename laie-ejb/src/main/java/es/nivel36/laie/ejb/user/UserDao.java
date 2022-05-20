package es.nivel36.laie.ejb.user;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;

import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.login.Credential;
import es.nivel36.login.LoginToken.TokenType;


public class UserDao extends AbstractDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Inject
	private SearchFacade searchFacade;

	public void insert(final User user) {
		Objects.requireNonNull(user);
		this.em.persist(user);
		if (user.getManager() != null) {
			this.insertUserClosures(user);
		}
	}

	public User update(final User user) {
		Objects.requireNonNull(user);
		this.updateUserClosures(user);
		return em.merge(user);
	}

	public boolean checkDuplicateEmail(final String email) {
		Objects.requireNonNull(email);
		return this.checkDuplicateField(User.class, "email", email);
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		final String namedQuery = "User.findSubordinateUsers";
		Parameters parameters = map("user", user);
		return this.findByQuery(User.class, namedQuery, parameters, Page.ALL_RESULTS);
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			final String namedQuery = "User.findByEmail";
			final Parameters parameters = map("email", email);
			return this.findByQuery(User.class, namedQuery, parameters);
		} catch (final NoResultException e) {
			return null;
		}
	}

	public boolean isSubordinateUser(final User user, final User subordinate) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(subordinate);
		final String namedQuery = "User.isSubordinateUser";
		final Parameters parameters = map("manager", user).and("subordinate", subordinate);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public SearchResult<User> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "_name", "_surname", "_email" };
		return searchFacade.search(User.class, page, sortOrder, searchFacets, searchText, searchFields);
	}

	///////////////////////////////////////////////////////////////////////////
	// USER CLOSURES
	///////////////////////////////////////////////////////////////////////////

	private List<UserClosure> findAntecessorsUserClosures(final User user) {
		return this.findByQuery(UserClosure.class, "UserClosure.findAntecessorsUserClosures", map("user", user),
				Page.ALL_RESULTS);
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
		logger.trace("Insert in user closure table. Antecessor {}, descendant {}, pathLength {}", antecessor,
				descendant, pathLength);
		final UserClosure newUserClosure = new UserClosure(antecessor, descendant, pathLength);
		this.em.persist(newUserClosure);
	}
}