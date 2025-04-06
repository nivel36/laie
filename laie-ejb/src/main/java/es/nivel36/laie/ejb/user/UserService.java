package es.nivel36.laie.ejb.user;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.PhysicalFile;
import es.nivel36.laie.ejb.core.file.PhysicalFileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private @Inject PhysicalFileService fileService;
	private @Inject UserDao userDao;

	public void addUser(final User user) throws DuplicateEmailException, BadManagerException {
		Objects.requireNonNull(user);
		logger.debug("Adding user {}", user);
		final String email = user.getEmail();
		if (this.userDao.emailExists(email)) {
			throw new DuplicateEmailException();
		}

		if (user.getManager() != null) {
			this.validateManagerForUser(user);
		}
		this.userDao.insert(user);
		logger.trace("User {} added successfully", user);
	}

	private void validateManagerForUser(final User user) throws BadManagerException {
		final User manager = user.getManager();
		if (user.equals(manager)) {
			logger.warn("User {} cannot be their own manager", user);
			throw new BadManagerException("User cannot be their own manager");
		}
		if (this.userDao.isSubordinateUser(user, manager)) {
			logger.warn("Cannot assign manager {} to user {} because the manager is subordinate to the user", manager,
					user);
			throw new BadManagerException("User is already managing this manager");
		}
	}

	public User updateUser(final User user) throws DuplicateEmailException, BadManagerException {
		Objects.requireNonNull(user);
		logger.debug("Updating user {}", user);
		final User userInDatabase = this.userDao.find(User.class, user.getId());

		// Check if the e-mail has been modified and is being used by another user.
		final String newEmail = user.getEmail();
		final String oldEmail = userInDatabase.getEmail();
		this.checkDuplicateEmail(newEmail, oldEmail);

		// Check if the manager has changed and if he/she meets the requirements to be
		// the new manager.
		this.changeUserManager(userInDatabase, userInDatabase.getManager(), user.getManager());

		final PhysicalFile picture = user.getPicture();
		final PhysicalFile pictureInDatabase = userInDatabase.getPicture();
		if (hasPictureChanged(picture, pictureInDatabase) && pictureInDatabase != null) {
			this.fileService.removeFile(pictureInDatabase);
		}
		final User updatedUser = this.userDao.update(user);
		logger.trace("User {} updated successfully", updatedUser);
		return updatedUser;

	}

	private void checkDuplicateEmail(final String newEmail, final String oldEmail) throws DuplicateEmailException {
		if (!newEmail.equals(oldEmail)) {
			if (this.userDao.emailExists(newEmail)) {
				throw new DuplicateEmailException();
			}
		}
	}

	private boolean hasPictureChanged(final PhysicalFile picture, final PhysicalFile pictureInDatabase) {
		if (picture == null != (pictureInDatabase == null)) {
			return true;
		}
		if (picture == null && pictureInDatabase == null) {
			return false;
		}
		return !pictureInDatabase.equals(picture);
	}

	private void changeUserManager(final User user, final User oldManager, final User newManager)
			throws BadManagerException {
		// Deleting manager
		if (newManager == null && oldManager != null) {
			logger.debug("Removing manager from user {}", user);
			user.setManager(null);
			return;
		}

		// No changes
		if (newManager == null && oldManager == null || newManager.equals(oldManager)) {
			logger.debug("Manager not changed");
			return;
		}

		// Updating manager
		logger.debug("Changing manager from {} to {} of user {}", oldManager, newManager, user);

		if (user.equals(newManager)) {
			logger.warn("User {} cannot be their own manager", user);
			throw new BadManagerException("User cannot be their own manager");
		}
		if (this.userDao.isSubordinateUser(user, newManager)) {
			logger.warn("Cannot assign manager {} to user {} because the manager is subordinate to the user",
					newManager, user);
			throw new BadManagerException("User is already managing this manager");
		}
	}

	public User changeUserImage(final User user, final InputStream image) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(image);
		logger.debug("Updating image for user {}", user);
		final PhysicalFile newImage = this.fileService.uploadFile(image, true);
		final PhysicalFile oldImage = user.getPicture();
		user.setPicture(newImage);
		final User updatedUser = userDao.update(user);
		if (oldImage != null) {
			logger.trace("Removing user {} old image", user);
			this.fileService.removeFile(oldImage);
		}
		logger.trace("Image for user {} updated successfully", user);
		return updatedUser;
	}

	public User findUserById(final long userId) {
		logger.debug("Retrieving user by id {}", userId);
		final User user = this.userDao.find(User.class, userId);
		logger.trace("User {} found by id {}", user, userId);
		return user;
	}
	
	public User findSessionUserData(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Retrieving session user data by mail {}", email);
		final User user = this.userDao.findSessionUserData(email);
		logger.trace("Session User data {} found by mail{}", user, email);
		return user;
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Retrieving user by email {}", email);
		final User user = this.userDao.findUserByEmail(email);
		logger.trace("User {} found by email {}", user, email);
		return user;
	}

	public User findUserDetailsById(final long userId) {
		logger.debug("Retrieving user details by user id {}", userId);
		final User user = this.userDao.findUserDetailsById(userId);
		logger.trace("User details of user {} found by id {}", user, userId);
		return user;
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Retrieving subordinate users of user {}", user);
		final List<User> users = this.userDao.findSubordinateUsers(user);
		logger.trace("Subordinate users {} of user {} found ", users, user);
		return users;
	}

	public boolean isSubordinateUser(final User user, final User manager) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(manager);
		logger.debug("Checking if user {} is subordinate of {}", user, manager);
		boolean isSubordinateUser = this.userDao.isSubordinateUser(user, manager);
		if (isSubordinateUser) {
			logger.trace("User {} is subordinate user of {}", user, manager);
		} else {
			logger.trace("User {} isn't subordinate user of {}", user, manager);
		}
		return isSubordinateUser;
	}

	public SearchResult<User> searchUsers(final String searchText, final Page page) {
		return this.searchUsers(searchText, page, null, null);
	}

	public SearchResult<User> searchUsers(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		logger.debug("Executing user search with searchText: '{}', page: {}, sortField: {}, searchFacets: {}",
				searchText, page, sortField, (searchFacets != null ? Arrays.toString(searchFacets) : "none"));

		final SearchResult<User> result = this.userDao.searchUsers(searchText, page, sortField, searchFacets);
		logger.trace("User search completed with searchText: '{}'. Total results: {}.", searchText,
				result.total().hitCount());
		return result;
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = Objects.requireNonNull(userDao);
	}

	public void setFileService(final PhysicalFileService fileService) {
		this.fileService = Objects.requireNonNull(fileService);
	}
}