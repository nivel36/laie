package es.nivel36.laie.ejb.user;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class UserDao extends AbstractDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	private @Inject SearchFacade searchFacade;

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

	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		return this.fieldExists(User.class, "email", email);
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		final String jpql = """
					SELECT u
					FROM UserClosure uc
					JOIN uc.descendant u
					WHERE uc.ancestor = :user
					AND uc.pathLength > 0
				""";
		final TypedQuery<User> query = this.em.createQuery(jpql, User.class);
		query.setParameter("user", user);
		return query.getResultList();
	}
	
	public User findSessionUserData(final String email) {
		Objects.requireNonNull(email);
		try {
			final String jpql = """
						SELECT u
						FROM User u
						LEFT JOIN FETCH u.bookmarks
						WHERE u.email = :email
					""";
			final TypedQuery<User> query = this.em.createQuery(jpql, User.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			final String jpql = """
						SELECT u
						FROM User u
						WHERE u.email = :email
					""";
			final TypedQuery<User> query = this.em.createQuery(jpql, User.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public User findUserDetailsById(final long userId) {
		final String jpql = """
					SELECT u
					FROM User u
					LEFT JOIN FETCH u.manager
					LEFT JOIN FETCH u.team
					LEFT JOIN FETCH u.picture
					WHERE u.id = :userId
				""";
		final TypedQuery<User> query = this.em.createQuery(jpql, User.class);
		query.setParameter("userId", userId);
		return query.getSingleResult();
	}

	public boolean isSubordinateUser(final User manager, final User subordinate) {
		Objects.requireNonNull(manager);
		Objects.requireNonNull(subordinate);
		final String jpql = """
					SELECT CASE WHEN (COUNT(u) > 0) THEN TRUE ELSE FALSE END
					FROM UserClosure uc
					JOIN uc.descendant u
					WHERE uc.ancestor = :manager
					AND u = :subordinate
				""";
		final TypedQuery<Boolean> query = this.em.createQuery(jpql, Boolean.class);
		query.setParameter("manager", manager);
		query.setParameter("subordinate", subordinate);
		return query.getSingleResult();
	}

	public SearchResult<User> searchUsers(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "_name", "_surname", "_email" };
		return searchFacade.search(User.class, page, sortField, searchFacets, searchText, searchFields);
	}

	///////////////////////////////////////////////////////////////////////////
	// USER CLOSURES
	///////////////////////////////////////////////////////////////////////////

	private List<UserClosure> findUserAncestorClosures(final User user) {
		final String jpql = """
					SELECT uc
					FROM UserClosure uc
					WHERE uc.descendant = :user
				""";
		final TypedQuery<UserClosure> query = this.em.createQuery(jpql, UserClosure.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

	private void deleteUserClosures(final User user) {
		logger.trace("Delete user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = this.findUserAncestorClosures(user);
		for (final UserClosure userClosure : userClosures) {
			this.delete(UserClosure.class, userClosure);
		}
	}

	private void updateUserClosures(final User user) {
		final User userInDatabase = this.em.find(User.class, user.getId());
		if (userInDatabase == null) {
			logger.warn("User doesn't exist");
			throw new IllegalStateException();
		}
		final User newManager = user.getManager();
		final User oldManager = userInDatabase.getManager();
		if (this.isManagerBeingAdded(newManager, oldManager)) {
			this.insertUserClosures(user);
		} else if (this.isManagerBeingRemoved(newManager, oldManager)) {
			this.deleteUserClosures(userInDatabase);
		} else if (this.isManagerChanging(newManager, oldManager)) {
			this.deleteUserClosures(userInDatabase);
			this.insertUserClosures(user);
		}
	}

	private boolean isManagerBeingRemoved(final User newManager, final User oldManager) {
		return oldManager != null && newManager == null;
	}

	private boolean isManagerBeingAdded(final User newManager, final User oldManager) {
		return oldManager == null && newManager != null;
	}

	private boolean isManagerChanging(final User newManager, final User oldManager) {
		return oldManager != null && newManager != null && !newManager.equals(oldManager);
	}

	private void insertUserClosures(final User user) {
		logger.trace("Insert user closures for user {}", user.getEmail());
		final List<UserClosure> userClosures = this.findUserAncestorClosures(user.getManager());
		for (final UserClosure userClosure : userClosures) {
			this.insertUserClosure(userClosure.getAncestor(), user, userClosure.getPathLength() + 1);
		}
		this.insertUserClosure(user, user, 0);
	}

	private void insertUserClosure(final User ancestor, final User descendant, final int pathLength) {
		logger.trace("Insert into user closure table. Ancestor {}, descendant {}, pathLength {}", ancestor,
				descendant, pathLength);
		final UserClosure newUserClosure = new UserClosure(ancestor, descendant, pathLength);
		this.em.persist(newUserClosure);
	}
}