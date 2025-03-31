package es.nivel36.laie.ejb.user;

import java.io.InputStream;
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
		logger.debug("Add user {}", user);
		final String email = user.getEmail();
		if (this.userDao.checkDuplicateEmail(email)) {
			throw new DuplicateEmailException();
		}

		if (user.getManager() != null) {
			this.validateUserManager(user);
		}
		this.userDao.insert(user);
	}

	private void validateUserManager(final User user) throws BadManagerException {
		final User manager = user.getManager();
		if (user.equals(manager)) {
			logger.warn("User {} cannot be their own manager", user);
			throw new BadManagerException("User cannot be their own manager");
		}
		if (this.userDao.isSubordinateUser(user, manager)) {
			logger.warn("The user {} is already managing {}", user, manager);
			throw new BadManagerException("The user is already managing the new manager");
		}
	}

	public User updateUser(final User user) throws DuplicateEmailException, BadManagerException {
		Objects.requireNonNull(user);
		logger.debug("Update user {}", user);
		final User userInDatabase = this.userDao.find(User.class, user.getId());

		// Check if the e-mail has been modified and is being used by another user.
		final String newEmail = user.getEmail();
		final String oldEmail = userInDatabase.getEmail();
		this.checkDuplicateEmail(newEmail, oldEmail);

		// Check if the manager has changed and if he/she meets the requirements to be
		// the new manager.
		this.changeUsersManager(userInDatabase, userInDatabase.getManager(), user.getManager());

		final PhysicalFile picture = user.getPicture();
		final PhysicalFile pictureInDatabase = userInDatabase.getPicture();
		if (pictureHasChanged(picture, pictureInDatabase) && pictureInDatabase != null) {
			this.fileService.removeFile(pictureInDatabase);
		}

		return this.userDao.update(user);
	}
	
	private void checkDuplicateEmail(final String newEmail, final String oldEmail) throws DuplicateEmailException {
		if (!newEmail.equals(oldEmail)) {
			if (this.userDao.checkDuplicateEmail(newEmail)) {
				throw new DuplicateEmailException();
			}
		}
	}

	private boolean pictureHasChanged(final PhysicalFile picture, final PhysicalFile pictureInDatabase) {
		if (picture == null != (pictureInDatabase == null)) {
			return true;
		}
		if (picture == null && pictureInDatabase == null) {
			return false;
		}
		return !pictureInDatabase.equals(picture);
	}

	private void changeUsersManager(final User user, final User oldManager, final User newManager)
			throws BadManagerException {
		// Deleting manager
		if (newManager == null && oldManager != null) {
			logger.debug("Delete manager to user {}", user);
			user.setManager(null);
			return;
		}

		// No changes
		if (newManager == null && oldManager == null || newManager.equals(oldManager)) {
			logger.debug("Manager not changed");
			return;
		}

		// Updating manager
		logger.debug("Change manager from {} to {} of user {}", oldManager, newManager, user);

		if (user.equals(newManager)) {
			logger.warn("User {} cannot be their own manager", user);
			throw new BadManagerException("User cannot be their own manager");
		}
		if (this.userDao.isSubordinateUser(user, newManager)) {
			logger.warn("The user {} is already managing {}", user, newManager);
			throw new BadManagerException("The user is already managing the new manager");
		}
	}

	public User changeUsersImage(final User user, final InputStream image) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(image);
		logger.debug("Updating image for user {}", user);
		final PhysicalFile newImage = this.fileService.uploadFile(image, true);
		final PhysicalFile oldImage = user.getPicture();
		user.setPicture(newImage);
		final User updatedUser = userDao.update(user);
		if (oldImage != null) {
			logger.trace("Remove user {} old image", user);
			this.fileService.removeFile(oldImage);
		}
		return updatedUser;
	}

	public User findUserById(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Find user by id {}", id);
		return this.userDao.find(User.class, id);
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find user by email {}", email);
		return this.userDao.findUserByEmail(email);
	}

	public User findAllUserData(final long userId) {
		if (userId <= 0) {
			throw new IllegalStateException("User ID must be greater than zero. Received: " + userId);
		}
		logger.debug("Find all user data by user id {}", userId);
		return this.userDao.findAllUserData(userId);
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Finding subordinate users of user {}", user);
		return this.userDao.findSubordinateUsers(user);
	}

	public boolean isSubordinateUser(final User user, final User manager) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(manager);
		logger.debug("Checking if user {} is subordinate of {}", user, manager);
		return this.userDao.isSubordinateUser(user, manager);
	}

	public SearchResult<User> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<User> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		return this.userDao.search(searchText, page, sortField, searchFacets);
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = Objects.requireNonNull(userDao);
	}

	public void setFileService(final PhysicalFileService fileService) {
		this.fileService = Objects.requireNonNull(fileService);
	}
}