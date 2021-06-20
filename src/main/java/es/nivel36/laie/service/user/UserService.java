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
package es.nivel36.laie.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.service.Repository;

@Stateless
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	// The limit of the maximum number of results that can be searched.
	private static final int MAX_RESULT_SIZE = 100;

	@Inject
	@Repository
	private UserJpaDao userDao;

	///////////////////////////////////////////////////////////////////////////
	// PUBLIC
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns all users.
	 *
	 * @param page     <tt>int</tt> with the page to start searching from. Must be
	 *                 greater or equal than 0.
	 * @param pageSize <tt>int</tt> with the maximum number of results to return.
	 *                 Must be greater than 0 and lesser or equal than 100.
	 *
	 * @return <tt> List<UserDto></tt> with all the users. The size of the list is
	 *         the same as the size of the page.
	 */
	public List<UserDto> findAll(final int page, final int pageSize) {
		validatePagination(page, pageSize);
		logger.debug("Find all users, page {}, page size", page, pageSize);
		final List<User> users = this.userDao.findAll(page, pageSize);
		return mapUserDtoList(users);
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
	 * @throws NoResultException if no user with such an identifier is found.
	 */
	public UserDto findUserByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find user by uid {}", uid);
		final User user = this.userDao.findUserByUid(uid);
		final UserDtoMapper mapper = new UserDtoMapper(user);
		return mapper.map();
	}

	/**
	 * Finds all users whose manager is the user passed as a parameter.
	 *
	 * @param manager  <tt>UserDto</tt> with the manager of all the users that will
	 *                 be returned in the query. This parameter cannot be null.
	 * @param page     <tt>int</tt> with the page to start searching from. Must be
	 *                 greater or equal than 0.
	 * @param pageSize <tt>int</tt> with the maximum number of results to return.
	 *                 Must be greater than 0 and lesser or equal than 100
	 *
	 * @return <tt> List<UserDto></tt> whose manager is the user passed as a
	 *         parameter. The size of the list is the same as the size of the page.
	 */
	public List<UserDto> findUsersByManager(final User manager, final int page, final int pageSize) {
		Objects.requireNonNull(manager);
		validatePagination(page, pageSize);
		logger.debug("Find users by manager {}, page {}, page size", manager, page, pageSize);
		final List<User> users = this.userDao.findUsersByManager(manager, page, pageSize);
		return mapUserDtoList(users);
	}

	/**
	 * Checks whether a manager is a manager of a user. This implies not only if
	 * he/she is the direct manager of the user but also if he/she is above him/her
	 * in the hierarchy pyramid.
	 * 
	 * @param userUid    <tt>String</tt> with the identifier of the user we want to
	 *                   verify if he/she is a subordinate.
	 * @param managerUid <tt>String</tt> with the identifier of the user we want to
	 *                   verify if he/she is a manager.
	 * @return <tt>boolean</tt> with <tt>true</tt> if the manager passed by
	 *         parameter is manager of the user. <tt>false</tt> otherwise.
	 */
	public boolean isSubordinateUser(final String userUid, final String managerUid) {
		Objects.requireNonNull(userUid);
		Objects.requireNonNull(managerUid);
		final User user = userDao.findUserByUid(userUid);
		if (user.getManager().getUid().equals(managerUid)) {
			return true;
		} else {
			final User manager = userDao.findUserByUid(managerUid);
			return this.userDao.isSubordinateUser(user, manager);
		}
	}

	/**
	 * Insert a user in the database.
	 *
	 * @param user <tt>UserDto</tt> to insert. This parameter cannot be null
	 */
	public UserDto insert(UserDto user) throws DuplicateEmailException, DuplicateIdNumberException {
		Objects.requireNonNull(user);
		logger.debug("Persist user {}", user);

		validateDuplicateEmail(user.getEmail());
		validateDuplicateIdNumber(user.getIdNumber());

		final User userToPersist = new User();
		this.mergeUserDto(user, userToPersist);
		this.userDao.insert(userToPersist);
		final UserDtoMapper mapper = new UserDtoMapper(userToPersist);
		return mapper.map();
	}

	/**
	 * Insert a user in the database.
	 *
	 * @param user <tt>UserDto</tt> to insert. This parameter cannot be null
	 *
	 * @return <tt>UserDto<tt> inserted.
	 */
	public UserDto update(final UserDto user) throws DuplicateEmailException, DuplicateIdNumberException {
		Objects.requireNonNull(user);
		logger.debug("Update user {}", user);
		validateDuplicateEmail(user.getEmail());
		validateDuplicateIdNumber(user.getIdNumber());
		final User userInDatabase = this.userDao.findUserByUid(user.getUid());
		this.mergeUserDto(user, userInDatabase);
		final UserDtoMapper userDtoMapper = new UserDtoMapper(userInDatabase);
		return userDtoMapper.map();
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
	 * @param userUid       <tt>String</tt> with the unique identifier of the user
	 *                      to which we are going to update the manager. This
	 *                      parameter cannot be null.
	 * @param newManagerUid <tt>String</tt> with the unique identifier of the new
	 *                      manager. <tt>null</tt> if we are going to remove the
	 *                      manager.
	 * @return <tt>UserDto</tt> with the new manager already informed.
	 * @throws InvalidManagerException if any of the rules for updating the new
	 *                                 manager are not met.
	 */
	public UserDto updateManager(final String userUid, final String newManagerUid) throws InvalidManagerException {
		Objects.requireNonNull(userUid);
		if (userUid == newManagerUid) {
			throw new InvalidManagerException("User can't be his/her own manager");
		}
		final User userInDatabase = userDao.findUserByUid(userUid);
		final User newManagerInDatabase;
		if (newManagerUid != null) {
			newManagerInDatabase = userDao.findUserByUid(newManagerUid);

			// check for circular references
			final boolean userIsManagerOfHisNewManager = this.userDao.isSubordinateUser(newManagerInDatabase,
					userInDatabase);
			if (userIsManagerOfHisNewManager) {
				throw new InvalidManagerException("User is the manager of his/her new manager");
			}
		} else {
			newManagerInDatabase = null;
		}
		final User savedUser = this.userDao.updateManager(userInDatabase, newManagerInDatabase);
		final UserDtoMapper userMapper = new UserDtoMapper(savedUser);
		return userMapper.map();
	}

	///////////////////////////////////////////////////////////////////////////
	// PRIVATE
	///////////////////////////////////////////////////////////////////////////

	private List<UserDto> mapUserDtoList(final List<User> users) {
		final List<UserDto> usersToReturn = new ArrayList<>(users.size());
		for (final User user : users) {
			final UserDtoMapper userDtoMapper = new UserDtoMapper(user);
			final UserDto userDto = userDtoMapper.map();
			usersToReturn.add(userDto);
		}
		return usersToReturn;
	}

	private void mergeUserDto(final UserDto user, final User userInDatabase) {
		final User manager = findManager(user, userInDatabase);
		userInDatabase.setManager(manager);
		userInDatabase.setEmail(user.getEmail());
		userInDatabase.setIdNumber(user.getIdNumber());
		userInDatabase.setName(user.getName());
		userInDatabase.setPhoneNumber(user.getPhoneNumber());
		userInDatabase.setRole(user.getRole());
		userInDatabase.setSurname(user.getSurname());
	}

	private User findManager(final UserDto user, final User userInDatabase) {
		final String managerUid = user.getManagerUid();
		final User managerInDatabase = userInDatabase.getManager();

		if ((managerUid != null && managerInDatabase == null)
				|| (managerUid != null && !managerUid.equals(managerInDatabase.getUid()))) {
			// The manager has changed. We look for the new manager in the database.
			final User manager = this.userDao.findUserByUid(managerUid);
			return manager;
		} else if (managerUid == null && managerInDatabase != null) {
			// The user has no manager.
			return null;
		}
		// No changes
		return userInDatabase.getManager();
	}

	private void validateDuplicateIdNumber(final String idNumber) throws DuplicateIdNumberException {
		if (idNumber != null) {
			boolean duplicateIdNumber = this.userDao.findDuplicateIdNumber(idNumber);
			if (duplicateIdNumber) {
				throw new DuplicateIdNumberException(idNumber);
			}
		}
	}

	private void validateDuplicateEmail(final String email) throws DuplicateEmailException {
		boolean duplicateEmail = this.userDao.findDuplicateEmail(email);
		if (duplicateEmail) {
			throw new DuplicateEmailException(email);
		}
	}

	private void validatePagination(final int page, final int pageSize) {
		if (page < 0) {
			throw new IllegalArgumentException("Bad page number");
		}
		if (pageSize <= 0 || pageSize > MAX_RESULT_SIZE) {
			throw new IllegalArgumentException("Bad page size number");
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// SETTERS
	///////////////////////////////////////////////////////////////////////////

	public void setUserDao(final UserJpaDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}
