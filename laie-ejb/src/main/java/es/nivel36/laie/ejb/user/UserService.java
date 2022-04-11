package es.nivel36.laie.ejb.user;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileJpaDao;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;

@Stateless
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Inject
	private FileService fileService;

	@Inject
	@Repository
	private FileJpaDao fileDao;

	@Inject
	@Repository
	private UserDao userDao;

	public void addUser(final User user) throws DuplicateEmailException, BadManagerException {
		Objects.requireNonNull(user);
		logger.debug("Insert user {}", user);
		final String email = user.getEmail();
		if (this.userDao.checkDuplicateEmail(email)) {
			throw new DuplicateEmailException();
		}

		if (user.getManager() != null) {
			this.addUsersManager(user);
		}

		this.userDao.insert(user);
	}

	private void checkDuplicateEmail(final String newEmail, final String oldEmail) throws DuplicateEmailException {
		if (!newEmail.equals(oldEmail)) {
			if (this.userDao.checkDuplicateEmail(newEmail)) {
				throw new DuplicateEmailException();
			}
		}
	}

	private void addUsersManager(final User user) throws BadManagerException {
		final User manager = user.getManager();
		if (user.equals(manager)) {
			logger.warn("The user {} can't be his/her manager", user);
			throw new BadManagerException("User can't be his/her manager");
		}
		if (this.userDao.isSubordinateUser(user, manager)) {
			logger.warn("The user {} is the manager of {}", user, manager);
			throw new BadManagerException("User is the manager of his/her new manager");
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
		changeUsersManager(userInDatabase, userInDatabase.getManager(), user.getManager());

		final File picture = user.getPicture();
		final File pictureInDatabase = userInDatabase.getPicture();
		if (pictureHasChanged(picture, pictureInDatabase)) {
			this.fileService.removeFile(pictureInDatabase);
		}

		return this.userDao.update(user);
	}

	private boolean pictureHasChanged(final File picture, final File pictureInDatabase) {
		if (picture == null && pictureInDatabase != null) {
			return true;
		}
		if (picture != null && pictureInDatabase == null) {
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
		if ((newManager == null && oldManager == null) || newManager.equals(oldManager)) {
			logger.debug("Manager not changed");
			return;
		}

		// Updating manager
		logger.debug("Change manager from {} to {} of user {}", oldManager, newManager, user);
		if (user.equals(newManager)) {
			logger.warn("The user {} can't be his/her manager", user);
			throw new BadManagerException("User can't be his/her manager");
		}
		if (this.userDao.isSubordinateUser(user, newManager)) {
			logger.warn("The user {} is the manager of {}", user, newManager);
			throw new BadManagerException("User is the manager of his new manager");
		}
	}

	public void changeUsersImage(final User user, final InputStream image) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(image);
		logger.debug("Change image to user {}", user);
		final Long userId = user.getId();
		final File newImage = this.fileService.uploadFile(image, userId + "_picture", true);
		final File file = fileDao.find(File.class, newImage.getId());
		final File oldImage = user.getPicture();
		if (oldImage != null) {
			logger.trace("Remove user {} old image", user);
			this.fileService.removeFile(oldImage);
		}
		user.setPicture(file);
		userDao.update(user);
	}

	public void changePassword(final String email, final String oldPassword, final String newPassword) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(newPassword);
		logger.debug("Change password for user {}", email);
		// TODO: añadir lógica con el password antiguo
		final Credential credential = this.userDao.findCredential(email);
		credential.setPassword(newPassword);
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

	public Credential findCredential(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find credential for user with email {}", email);
		return this.userDao.findCredential(email);
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Finding subordinate users of user {}", user);
		return this.userDao.findSubordinateUsers(user);
	}

	public boolean isSubordinateUser(final User user, final User manager) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(manager);
		logger.debug("Find is user {} is subordinate of {}", user, manager);
		return this.userDao.isSubordinateUser(user, manager);
	}

	public SearchResult<User> search(final String searchText, final Page page) {
		return this.search(searchText, page, null, null);
	}

	public SearchResult<User> search(final String searchText, final Page page, final SortField sortField,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(page);
		return this.userDao.search(searchText, page, sortField, searchFacets);
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}