package ged.ejb.user;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractIndexedService;
import ged.ejb.core.file.FileService;
import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.security.LoginToken.TokenType;

@Stateless
public class UserService extends AbstractIndexedService<User> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private transient FileService fileService;

	@Inject
	@Repository
	private UserDao userDao;

	public User addUserImage(final User user, final InputStream inputStream) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(inputStream);

		final String oldImage = user.getImageFileName();
		final String uuid = this.fileService.uploadImage(inputStream);
		user.setImageFileName(uuid);
		final User savedUser = this.save(user);

		if (oldImage != null) {
			this.fileService.removeFile(oldImage);
		}
		return savedUser;
	}

	public void changePassword(final User user, final String newPassword) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(newPassword);
		logger.debug("Change password for user {}", user);
		final Credential credential = this.userDao.findCredential(user.getEmail());
		credential.setPassword(newPassword);
	}

	public User findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find user by uid {}", uid);
		return this.userDao.findByUid(uid);
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

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find user by email {}", email);
		return this.userDao.findUserByEmail(email);
	}

	public User findUserByTokenHash(final byte[] tokenHash, final TokenType type) {
		Objects.requireNonNull(tokenHash);
		Objects.requireNonNull(type);
		logger.debug("Find user by token hash {} and type {}", tokenHash, type);
		return this.userDao.findUserByTokenHashAndType(tokenHash, type);
	}

	@Override
	protected AbstractIndexedDao<User> getDao() {
		return this.userDao;
	}

	private boolean hasValidManager(final User user) {
		final User manager = user.getManager();
		return !(user.isAdmin() && (manager != null));
	}

	public boolean isEmailInUse(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Testing if email {} is in use", email);
		return this.userDao.isEmailInUse(email);
	}

	public boolean isSubordinateUser(final User manager, final User subordinate) {
		Objects.requireNonNull(manager);
		Objects.requireNonNull(subordinate);
		logger.debug("Testing if user {} is manager of the user {}", manager.getEmail(), subordinate.getEmail());
		return this.findSubordinateUsers(manager).contains(subordinate);
	}

	public User login(final String email, final String password) throws LoginException {
		Objects.requireNonNull(email);
		Objects.requireNonNull(password);
		final Credential credential = this.userDao.findCredential(email);
		if (credential == null) {
			throw new LoginException("Invalid email");
		}
		if (!credential.isValid(password)) {
			throw new LoginException("Passwords doesn't match");
		}
		final User user = credential.getUser();
		user.setLastConnection(LocalDateTime.now());
		return super.save(user);
	}

	@Override
	public User save(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Saving user {}", user.getEmail());
		this.validateManager(user);
		return super.save(user);
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}

	private void validateManager(final User user) {
		if (user.equals(user.getManager())) {
			logger.warn("The user {} can't be his/her manager", user.getEmail());
			throw new BadManagerException("User can't be his/her manager");
		}
		if (!this.hasValidManager(user)) {
			logger.warn("The user {} has an admin role but has {} as a manager", user.getEmail(),
					user.getManager().getRole());
			throw new BadManagerException("Admins can't have a manager");
		}
	}
}