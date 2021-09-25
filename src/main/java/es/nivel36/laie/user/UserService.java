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

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.core.service.AbstractService;
import es.nivel36.laie.core.service.Repository;
import es.nivel36.laie.core.service.search.SearchFacade;
import es.nivel36.laie.core.service.search.SortField;
import es.nivel36.laie.department.Department;
import es.nivel36.laie.department.DepartmentJpaDao;
import es.nivel36.laie.department.SimpleDepartmentDto;
import es.nivel36.laie.file.File;
import es.nivel36.laie.file.FileJpaDao;

@Stateless
public class UserService extends AbstractService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	////////////////////////////////////////////////////////////////////////////

	private final UserMapper userMapper;

	@Inject
	@Repository
	private SearchFacade searchFacade;

	@Inject
	@Repository
	private DepartmentJpaDao departmentDao;

	@Inject
	@Repository
	private UserJpaDao userDao;

	@Inject
	@Repository
	private FileJpaDao fileDao;

	////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTOR
	////////////////////////////////////////////////////////////////////////////

	public UserService() {
		this.userMapper = new UserMapper();
	}

	////////////////////////////////////////////////////////////////////////////
	// PUBLIC
	////////////////////////////////////////////////////////////////////////////

	/**
	 * Associates an image with the user. If you want to delete an image associated
	 * with a user, the imageUid parameter must be null.
	 *
	 * @param userUid  <tt>String</tt> with the unique identifier of the user to
	 *                 whom an image is assigned.
	 * @param imageUid <tt>String</tt> the unique identifier of the image.
	 *                 <tt>null</tt> if you want to delete an image,
	 *
	 */
	public void addImage(final String userUid, final String imageUid) {
		Objects.requireNonNull(userUid);
		final User user = this.userDao.findUserByUid(userUid);
		if (imageUid == null) {
			user.setImage(null);
		} else {
			final File file = fileDao.findByUid(imageUid);
			user.setImage(file);
		}
	}

	/**
	 * Returns all users.
	 *
	 * @param page     <tt>int</tt> with the page to start searching from. Must be
	 *                 greater or equal than 0.
	 * @param pageSize <tt>int</tt> with the maximum number of results to return.
	 *                 Must be greater than 0 and lesser or equal than 1000.
	 *
	 * @return <tt> List<UserDto></tt> with all the users. The size of the list is
	 *         the same as the size of the page.
	 */
	public List<UserDto> findAll(final int page, final int pageSize) {
		validatePagination(page, pageSize);
		logger.debug("Find all users, page {}, page size", page, pageSize);
		final List<User> users = this.userDao.findAll(page, pageSize);
		return userMapper.mapList(users);
	}

	/**
	 * Search for a user by their email.
	 *
	 * @param email <tt>String</tt> with the user's email. This parameter cannot be
	 *              null.
	 *
	 * @return <tt>User</tt> that has the same email as the one passed as parameter.
	 *         <tt>null</tt> if no user with such an email is found.
	 */
	public UserDto findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find user by email {}", email);
		final User user = this.userDao.findUserByEmail(email);
		return userMapper.map(user);
	}

	/**
	 * Search for a user by their unique identifier.
	 *
	 * @param uid <tt>String</tt> with the unique identifier. This parameter cannot
	 *            be null.
	 *
	 * @return <tt>User</tt> that has the same identifier as the one passed as
	 *         parameter. <tt>null</tt> if no user with such an identifier is found.
	 */
	public UserDto findUserByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find user by uid {}", uid);
		final User user = this.userDao.findUserByUid(uid);
		return userMapper.map(user);
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
	 *                   Must be greater than 0 and lesser or equal than 1000
	 *
	 * @return <tt> List<UserDto></tt> whose manager is the user passed as a
	 *         parameter. The size of the list is the same as the size of the page.
	 */
	public List<UserDto> findUsersByManager(final String managerUid, final int page, final int pageSize) {
		Objects.requireNonNull(managerUid);
		validatePagination(page, pageSize);
		logger.debug("Find users by manager {}, page {}, page size", managerUid, page, pageSize);
		final List<User> users = this.userDao.findUsersByManager(managerUid, page, pageSize);
		return userMapper.mapList(users);
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
		this.mergeUser(user, userToPersist);
		this.userDao.insert(userToPersist);
		return userMapper.map(userToPersist);
	}

	public List<UserDto> searchByName(final int firstResult, final int maxResults, final SortField sortField,
			final String searchText) {
		List<User> search = searchFacade.search(User.class, firstResult, maxResults, sortField, searchText,
				new String[] { "_name", "_surname", "_email" });
		return userMapper.mapList(search);
	}

	/**
	 * Update a user in the database.
	 *
	 * @param user <tt>UserDto</tt> to update. This parameter cannot be null
	 *
	 */
	public void update(final UserDto user) throws DuplicateEmailException, DuplicateIdNumberException {
		Objects.requireNonNull(user);
		logger.debug("Update user {}", user);
		final String uid = user.getUid();
		final User userInDatabase = this.userDao.findUserByUid(uid);
		final String newEmail = user.getEmail();
		if (!userInDatabase.getEmail().equals(newEmail)) {
			validateDuplicateEmail(newEmail);
		}
		final String idNumber = userInDatabase.getIdNumber();
		final String newIdNumber = user.getIdNumber();
		if (idNumber != null && !idNumber.equals(newIdNumber)) {
			validateDuplicateIdNumber(newIdNumber);
		}
		this.mergeUser(user, userInDatabase);
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
		if (userInDatabase == null) {
			final String message = String.format("User with uid %s not found", userUid);
			throw new InvalidManagerException(message);
		}
		final User newManagerInDatabase;
		if (newManagerUid != null) {
			newManagerInDatabase = userDao.findUserByUid(newManagerUid);
			if (newManagerInDatabase == null) {
				final String message = String.format("User with uid %s not found", newManagerUid);
				throw new InvalidManagerException(message);
			}
			final boolean isACircularReference = this.userDao.isSubordinateUser(newManagerInDatabase, userInDatabase);
			if (isACircularReference) {
				throw new InvalidManagerException("User is the manager of his/her new manager");
			}
		} else {
			newManagerInDatabase = null;
		}
		final User savedUser = this.userDao.updateManager(userInDatabase, newManagerInDatabase);
		return userMapper.map(savedUser);
	}

	///////////////////////////////////////////////////////////////////////////
	// PRIVATE
	///////////////////////////////////////////////////////////////////////////

	private void mergeUser(final UserDto user, final User userInDatabase) {
		final User manager = findManager(user, userInDatabase);
		userInDatabase.setManager(manager);
		final SimpleDepartmentDto simpleDepartment = user.getDepartment();
		if (simpleDepartment != null) {
			final Department department = departmentDao.findDepartmentByUid(simpleDepartment.getUid());
			userInDatabase.setDepartment(department);
		}
		userInDatabase.setEmail(user.getEmail());
		userInDatabase.setIdNumber(user.getIdNumber());
		userInDatabase.setJobPosition(user.getJobPosition());
		userInDatabase.setLocale(user.getLocale());
		userInDatabase.setName(user.getName());
		userInDatabase.setPhoneNumber(user.getPhoneNumber());
		userInDatabase.setRole(user.getRole());
		userInDatabase.setSurname(user.getSurname());
	}

	private User findManager(final UserDto user, final User userInDatabase) {
		final SimpleUserDto manager = user.getManager();
		final User managerInDatabase = userInDatabase.getManager();
		if ((manager != null && managerInDatabase == null)
				|| (manager != null && !manager.getUid().equals(managerInDatabase.getUid()))) {
			// The manager has changed. We look for the new manager in the database.
			return this.userDao.findUserByUid(manager.getUid());
		} else if (manager == null && managerInDatabase != null) {
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
				throw new DuplicateIdNumberException(
						String.format("There is already another user with the identifier %s", idNumber));
			}
		}
	}

	private void validateDuplicateEmail(final String email) throws DuplicateEmailException {
		boolean duplicateEmail = this.userDao.findDuplicateEmail(email);
		if (duplicateEmail) {
			throw new DuplicateEmailException(String.format("There is already another user with the email %s", email));
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// SETTERS
	///////////////////////////////////////////////////////////////////////////

	public void setUserDao(final UserJpaDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}

	public void setDepartmentDao(final DepartmentJpaDao departmentDao) {
		Objects.requireNonNull(departmentDao);
		this.departmentDao = departmentDao;
	}

	public void setFileDao(final FileJpaDao fileDao) {
		Objects.requireNonNull(fileDao);
		this.fileDao = fileDao;
	}
}
