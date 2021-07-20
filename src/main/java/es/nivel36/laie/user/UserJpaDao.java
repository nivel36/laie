/*
* Laie
* Copyright (C) 2021  Abel Ferrer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.nivel36.laie.user;

import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import es.nivel36.laie.core.service.AbstractDao;
import es.nivel36.laie.core.service.IdentifiableNotFoundException;
import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.login.LaieCredential;

/**
 * Data access object for the User entity.
 *
 * @author Abel Ferrer
 *
 */
@Repository
public class UserJpaDao extends AbstractDao {

	///////////////////////////////////////////////////////////////////////////
	// PUBLIC
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns all users.
	 *
	 * @param page     <tt>int</tt> with the page to start searching from. Must be
	 *                 greater or equal than 0.
	 * @param pageSize <tt>int</tt> with the maximum number of results to return.
	 *                 Must be greater than 0 and lesser or equal than 1000.
	 *
	 * @return <tt> List<User></tt> with all the users. The size of the list is the
	 *         same as the size of the page.
	 */
	public List<User> findAll(final int page, final int pageSize) {
		this.validatePagination(page, pageSize);
		final TypedQuery<User> query = this.entityManager.createNamedQuery("User.findAll", User.class);
		this.paginate(page, pageSize, query);
		return query.getResultList();
	}
	
	public LaieCredential findCredential(final String email) {
		final TypedQuery<LaieCredential> query = this.entityManager.createNamedQuery("User.findCredential", LaieCredential.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}
	
	public User findUserByTokenHashAndType(final byte[] tokenHash, final es.nivel36.laie.login.token.LoginToken.TokenType type) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(type);
		final TypedQuery<User> query = this.entityManager.createNamedQuery("User.findByTokenHashAndType", User.class);
		query.setParameter("tokenHash", tokenHash);
		query.setParameter("type", type);
		return query.getSingleResult();
	}

	/**
	 * Searches for an email in the database to check if a user with that email
	 * already exists.
	 * 
	 * @param email <tt>String</tt> with the email to search.
	 * @return <tt>boolean</tt> with the result of the search. <tt>true</tt> if the
	 *         email is already registered in the database, <tt>false</tt>
	 *         otherwise.
	 */
	public boolean findDuplicateEmail(final String email) {
		final TypedQuery<Boolean> query = this.entityManager.createNamedQuery("User.findDuplicateEmail", Boolean.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}

	/**
	 * Searches for an employee ID number in the database to check if a user with
	 * that ID already exists.
	 * 
	 * @param idNumber <tt>String</tt> With the identification number to search
	 * @return <tt>boolean</tt> with the result of the search. <tt>true</tt> if the
	 *         identifier is already registered in the database, <tt>false</tt>
	 *         otherwise.
	 */
	public boolean findDuplicateIdNumber(final String idNumber) {
		final TypedQuery<Boolean> query = this.entityManager.createNamedQuery("User.findDuplicateIdNumber",
				Boolean.class);
		query.setParameter("idNumber", idNumber);
		return query.getSingleResult();
	}

	/**
	 * Search for a user by their email.
	 *
	 * @param uid <tt>String</tt> with the email. This parameter cannot be null.
	 *
	 * @return <tt>User</tt> that has the same email as the one passed as parameter,
	 *         <tt>null</tt> if no user is found
	 * 
	 */
	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			final TypedQuery<User> q = this.entityManager.createNamedQuery("User.findUserByEmail", User.class);
			q.setParameter("email", email);
			return q.getSingleResult();
		} catch (NoResultException e) {
			throw new IdentifiableNotFoundException();
		}
	}

	/**
	 * Search for a user by their unique identifier.
	 *
	 * @param uid <tt>String</tt> with the unique identifier. This parameter cannot
	 *            be null.
	 *
	 * @return <tt>User</tt> that has the same identifier as the one passed as
	 *         parameter.
	 * 
	 * @throws IdentifiableNotFoundException if the user isn't found.
	 */
	public User findUserByUid(final String uid) {
		Objects.requireNonNull(uid);
		try {
			final TypedQuery<User> q = this.entityManager.createNamedQuery("User.findUserByUid", User.class);
			q.setParameter("uid", uid);
			return q.getSingleResult();
		} catch (NoResultException e) {
			throw new IdentifiableNotFoundException();
		}
	}

	/**
	 * Finds all users whose manager is the user passed as a parameter.
	 *
	 * @param managerUid <tt>String</tt> with the manager's identifier of all the
	 *                   users that will be returned in the query. This parameter
	 *                   cannot be null.
	 * @param page       <tt>int</tt> with the page to start searching from. Must be
	 *                   greater or equal than 0.
	 * @param pageSize   <tt>int</tt> with the maximum number of results to return.
	 *                   Must be greater than 0 and lesser or equal than 1000.
	 *
	 * @return <tt> List<User></tt> whose manager is the user passed as a parameter.
	 *         The size of the list is the same as the size of the page.
	 */
	public List<User> findUsersByManager(final String managerUid, final int page, final int pageSize) {
		Objects.requireNonNull(managerUid);
		this.validatePagination(page, pageSize);
		final TypedQuery<User> query = this.entityManager.createNamedQuery("User.findUsersByManagerUid", User.class);
		query.setParameter("managerUid", managerUid);
		this.paginate(page, pageSize, query);
		return query.getResultList();
	}

	/**
	 * Insert a user in the database.
	 *
	 * @param user <tt>User</tt> to insert. This parameter cannot be null
	 */
	public void insert(final User user) {
		Objects.requireNonNull(user);
		this.generateUid(user);
		this.entityManager.persist(user);
	}

	/**
	 * Checks whether a manager is a manager of a user. This implies not only if
	 * he/she is the direct manager of the user but also if he/she is above him/her
	 * in the hierarchy pyramid.
	 * 
	 * @param user    <tt>User</tt> with the user we want to verify if he/she is a
	 *                subordinate
	 * @param manager <tt>User</tt> with the user we want to verify if he/she is a
	 *                manager
	 * @return <tt>boolean</tt> with <tt>true</tt> if the manager passed by
	 *         parameter is manager of the user. <tt>false</tt> otherwise.
	 */
	public boolean isSubordinateUser(final User user, final User manager) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(manager);
		final TypedQuery<Boolean> query = this.entityManager.createNamedQuery("User.isSubordinateUser", Boolean.class);
		query.setParameter("manager", manager);
		query.setParameter("subordinate", user);
		return query.getSingleResult();
	}

	/**
	 * Updates the user's manager.
	 * 
	 * <p>
	 * The manager rules are:
	 * <ul>
	 * <li>A user cannot be his own manager.</li>
	 * <li>There can be no circular references. A user cannot have as a manager
	 * someone who has the user's own manager.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param user       <tt>User</tt> with the user to which we are going to update
	 *                   the manager. This parameter cannot be null.
	 * @param newManager <tt>User</tt> with the new manager. <tt>null</tt> if we are
	 *                   going to remove the manager.
	 * @return <tt>UserDto</tt> with the new manager already informed.
	 * @throws InvalidManagerException if any of the rules for updating the new
	 *                                 manager are not met.
	 */
	public User updateManager(final User user, final User manager) {
		Objects.requireNonNull(user);
		final User managerInDatabase = user.getManager();
		if (managerInDatabase == null && manager != null) {
			this.insertUserClosures(user, manager);
		} else if (managerInDatabase != null && manager == null) {
			this.deleteUserClosures(user);
		} else if (managerInDatabase != null && manager != null && !manager.equals(managerInDatabase)) {
			this.deleteUserClosures(user);
			this.insertUserClosures(user, manager);
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////
	// PRIVATE
	///////////////////////////////////////////////////////////////////////////

	// user closure methods

	private void deleteUserClosures(final User user) {
		final List<UserClosure> userClosures = this.findAntecessors(user);
		for (final UserClosure userClosure : userClosures) {
			this.entityManager.remove(userClosure);
		}
	}

	private List<UserClosure> findAntecessors(final User user) {
		final TypedQuery<UserClosure> q = this.entityManager.createQuery("UserClosure.findAntecessorsById",
				UserClosure.class);
		q.setParameter("id", user.getId());
		return q.getResultList();
	}

	private void insertUserClosure(final User antecessor, final User descendant, final int pathLength) {
		final UserClosure newUserClosure = new UserClosure(antecessor, descendant, pathLength);
		this.entityManager.persist(newUserClosure);
	}

	private void insertUserClosures(final User user, final User newManager) {
		final List<UserClosure> userClosures = this.findAntecessors(newManager);
		for (final UserClosure userClosure : userClosures) {
			this.insertUserClosure(userClosure.getAntecessor(), user, userClosure.getPathLength() + 1);
		}
		this.insertUserClosure(user, user, 0);
	}

	// End of user closure methods

	protected boolean findDuplicateUid(final String uid) {
		final TypedQuery<Boolean> query = this.entityManager.createQuery("User.findDuplicateUid", Boolean.class);
		query.setParameter("uid", uid);
		return query.getSingleResult();
	}
}
