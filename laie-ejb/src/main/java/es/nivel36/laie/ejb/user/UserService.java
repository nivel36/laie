package es.nivel36.laie.ejb.user;

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

/**
 * Stateless EJB service for managing User entities.
 * <p>
 * Provides methods to add, update, find, and search Users, handle file uploads,
 * and enforce business rules such as email uniqueness and manager assignments.
 * <p>
 * It does not provide any method for deleting users. If you wish to delete a
 * user's information, you must use the anonymize function. This is done to
 * maintain the referential integrity between tables.
 */
@Stateless
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private @Inject PhysicalFileService fileService;
	private @Inject UserDao userDao;

	/**
	 * Adds a new User to the system.
	 * <p>
	 * To register the user, your email address must not have been used by a
	 * previous user and the manager (if provided) is not the User itself and not a
	 * subordinate of the User.
	 *
	 * @param user the {@link User} to add; must not be null
	 * @throws NullPointerException    if user is null
	 * @throws DuplicateEmailException if email already exists
	 * @throws BadManagerException     if assigned manager is invalid
	 */
	public void addUser(final User user) throws DuplicateEmailException, BadManagerException {
		Objects.requireNonNull(user, "User must not be null");
		logger.debug("Adding new User: {}", user);

		final String email = user.getEmail();
		if (this.userDao.emailExists(email)) {
			throw new DuplicateEmailException("Email already exists: " + email);
		}

		if (user.getManager() != null) {
			this.validateManagerForUser(user);
		}

		this.userDao.insert(user);
		logger.trace("User {} added successfully.", user);
	}

	private void validateManagerForUser(final User user) throws BadManagerException {
		Objects.requireNonNull(user, "User must not be null");
		final User manager = user.getManager();
		if (user.equals(manager)) {
			logger.warn("User {} cannot be their own manager.", user);
			throw new BadManagerException("User cannot be their own manager");
		}
		if (this.userDao.isSubordinateUser(user, manager)) {
			logger.warn("Cannot assign manager {} to user {} because the manager is subordinate to the user.", manager,
					user);
			throw new BadManagerException("User is already managing this manager");
		}
	}

	/**
	 * Updates an existing User.
	 * <p>
	 * To update the user, your email address must not have been used by a previous
	 * user and the manager (if provided) is not the User itself and not a
	 * subordinate of the User.
	 *
	 * @param user the {@link User} to update; must not be null
	 * @return the updated {@link User}
	 * @throws NullPointerException    if user is null
	 * @throws DuplicateEmailException if new email already exists
	 * @throws BadManagerException     if new manager is invalid
	 */
	public User updateUser(final User user) throws DuplicateEmailException, BadManagerException {
		Objects.requireNonNull(user, "User must not be null");
		logger.debug("Updating User: {}", user);

		final User persisted = this.userDao.find(User.class, user.getId());

		// Check email uniqueness
		final String newEmail = user.getEmail();
		final String oldEmail = persisted.getEmail();
		if (!newEmail.equals(oldEmail) && this.userDao.emailExists(newEmail)) {
			throw new DuplicateEmailException("Email already exists: " + newEmail);
		}

		// Validate manager change
		this.changeUserManager(user, persisted.getManager(), user.getManager());

		// Handle picture replacement
		final PhysicalFile newPic = user.getPicture();
		final PhysicalFile oldPic = persisted.getPicture();
		if (this.hasPictureChanged(newPic, oldPic) && (oldPic != null)) {
			this.fileService.removeFile(oldPic);
		}

		final User updated = this.userDao.update(user);
		logger.trace("User {} updated successfully.", updated);
		return updated;
	}

	private boolean hasPictureChanged(final PhysicalFile picture, final PhysicalFile pictureInDb) {
		if ((picture == null) != (pictureInDb == null)) {
			return true;
		}
		if (picture == null) {
			return false;
		}
		return !picture.equals(pictureInDb);
	}

	private void changeUserManager(final User user, final User oldManager, final User newManager)
			throws BadManagerException {
		// Removing manager
		if ((newManager == null) && (oldManager != null)) {
			logger.debug("Removing manager from User: {}", user);
			user.setManager(null);
			return;
		}

		// No change
		if (((newManager == null) && (oldManager == null)) || newManager.equals(oldManager)) {
			logger.debug("Manager not changed for User: {}", user);
			return;
		}

		// Assigning new manager
		logger.debug("Changing manager from {} to {} for User: {}", oldManager, newManager, user);

		if (user.equals(newManager)) {
			logger.warn("User {} cannot be their own manager.", user);
			throw new BadManagerException("User cannot be their own manager");
		}
		if (this.userDao.isSubordinateUser(user, newManager)) {
			logger.warn("Cannot assign manager {} to User {} because the manager is subordinate to the user.",
					newManager, user);
			throw new BadManagerException("User is already managing this manager");
		}
	}

	/**
	 * Finds a User by its unique identifier.
	 *
	 * @param userId the ID of the User to find
	 * @return the {@link User} with the given ID, or null if not found
	 */
	public User findUserById(final long userId) {
		logger.debug("Finding User by ID: {}", userId);
		final User user = this.userDao.find(User.class, userId);
		logger.trace("User {} found with ID {}.", user, userId);
		return user;
	}

	/**
	 * Retrieves session-specific data for a User by email.
	 *
	 * @param email the email of the User; must not be null
	 * @return the {@link User} session data
	 * @throws NullPointerException if email is null
	 */
	public User findSessionUserData(final String email) {
		Objects.requireNonNull(email, "Email must not be null");
		logger.debug("Finding session User data by email: {}", email);
		final User user = this.userDao.findSessionUserData(email);
		logger.trace("Session User data {} found for email {}.", user, email);
		return user;
	}

	/**
	 * Finds a User by email.
	 *
	 * @param email the email of the User; must not be null
	 * @return the {@link User} with the given email, or null if not found
	 * @throws NullPointerException if email is null
	 */
	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email, "Email must not be null");
		logger.debug("Finding User by email: {}", email);
		final User user = this.userDao.findUserByEmail(email);
		logger.trace("User {} found with email {}.", user, email);
		return user;
	}

	/**
	 * Retrieves detailed User information by ID.
	 *
	 * @param userId the ID of the User; must not be null
	 * @return the detailed {@link User} information
	 */
	public User findUserDetailsById(final long userId) {
		logger.debug("Finding User details by ID: {}", userId);
		final User user = this.userDao.findUserDetailsById(userId);
		logger.trace("User details {} found for ID {}.", user, userId);
		return user;
	}

	/**
	 * Finds Subordinate Users for a given User.
	 *
	 * @param user the {@link User} whose subordinates to retrieve; must not be null
	 * @return list of subordinate Users
	 * @throws NullPointerException if user is null
	 */
	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user, "User must not be null");
		logger.debug("Finding subordinate Users of User: {}", user);
		final List<User> subs = this.userDao.findSubordinateUsers(user);
		logger.trace("Subordinate Users {} found for User {}.", subs, user);
		return subs;
	}

	/**
	 * Checks if one User is subordinate to another.
	 *
	 * @param user    the {@link User} to check; must not be null
	 * @param manager the {@link User} manager; must not be null
	 * @return true if user is subordinate to manager, false otherwise
	 * @throws NullPointerException if either argument is null
	 */
	public boolean isSubordinateUser(final User user, final User manager) {
		Objects.requireNonNull(user, "User must not be null");
		Objects.requireNonNull(manager, "Manager must not be null");
		logger.debug("Checking if User {} is subordinate to {}", user, manager);
		final boolean result = this.userDao.isSubordinateUser(user, manager);
		logger.trace(result ? "User {} is subordinate to {}." : "User {} is not subordinate to {}.", user, manager);
		return result;
	}

	/**
	 * Searches for Users matching the given text with pagination.
	 *
	 * @param searchText the text to search for; may be null to retrieve all Users
	 * @param page       the {@link Page} object containing pagination settings;
	 *                   must not be null
	 * @return a {@link SearchResult} of matching Users
	 */
	public SearchResult<User> searchUsers(final String searchText, final Page page) {
		return this.searchUsers(searchText, page, null, null);
	}

	/**
	 * Searches for Users matching the given text with pagination, sorting, and
	 * facets.
	 *
	 * @param searchText   the text to search for; may be null to retrieve all Users
	 * @param page         the {@link Page} object containing pagination settings;
	 *                     must not be null
	 * @param sortField    the {@link SortField} to order results by; may be null
	 * @param searchFacets an array of facets to apply; may be null
	 * @return a {@link SearchResult} of matching Users
	 * @throws NullPointerException if page is null
	 */
	public SearchResult<User> searchUsers(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page, "Page must not be null");
		logger.debug("Searching Users with text: '{}', page: {}, sortField: {}, facets: {}", searchText, page,
				sortField, (searchFacets != null ? Arrays.toString(searchFacets) : "none"));

		final SearchResult<User> result = this.userDao.searchUsers(searchText, page, sortField, searchFacets);
		logger.trace("User search completed with text '{}' and total hits: {}.", searchText, result.total().hitCount());
		return result;
	}

	/**
	 * Sets the UserDao instance, primarily for testing.
	 *
	 * @param userDao the dao to set; must not be null
	 * @throws NullPointerException if userDao is null
	 */
	public void setUserDao(final UserDao userDao) {
		this.userDao = Objects.requireNonNull(userDao, "UserDao must not be null");
	}

	/**
	 * Sets the PhysicalFileService instance, primarily for testing.
	 *
	 * @param fileService the file service to set; must not be null
	 * @throws NullPointerException if fileService is null
	 */
	public void setFileService(final PhysicalFileService fileService) {
		this.fileService = Objects.requireNonNull(fileService, "FileService must not be null");
	}
}
