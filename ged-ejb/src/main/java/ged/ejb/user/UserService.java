package ged.ejb.user;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractIndexedService;
import ged.ejb.core.file.File;
import ged.ejb.core.file.FileService;
import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Repository;

@Stateless
public class UserService extends AbstractIndexedService<User> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private FileService fileService;

	@Inject
	@Repository
	private UserDao userDao;
	
	public void deleteUserImage(final String uid) {
		Objects.requireNonNull(uid);
		final User user = this.findByUid(uid);
		logger.debug("Delete image to user {}", user);
		final File oldImage = user.getPicture();
		user.setPicture(null);
		if (oldImage != null) {
			this.fileService.removeFile(oldImage);
		}
	}

	public File addUserImage(final String uid, final InputStream inputStream) {
		Objects.requireNonNull(uid);
		Objects.requireNonNull(inputStream);
		final User user = this.findByUid(uid);
		logger.debug("Add image to user {}", user);
		final File oldImage = user.getPicture();
		final File newImage = this.fileService.uploadFile(inputStream, user.getUid() + "_picture", true);
		user.setPicture(newImage);
		if (oldImage != null) {
			this.fileService.removeFile(oldImage);
		}
		return newImage;
	}

	public void changePassword(final String email, final String newPassword) {
		Objects.requireNonNull(email);
		Objects.requireNonNull(newPassword);
		logger.debug("Change password for user {}", email);
		final Credential credential = this.userDao.findCredential(email);
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

	public List<User> findSubordinateUsers(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Finding subordinate users of user {}", email);
		return this.userDao.findSubordinateUsers(email);
	}

	public User findByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find user by email {}", email);
		return this.userDao.findUserByEmail(email);
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

	public boolean isSubordinateUser(final String managerEmail, final String subordinateEmail) {
		Objects.requireNonNull(managerEmail);
		Objects.requireNonNull(subordinateEmail);
		logger.debug("Testing if user {} is manager of the user {}", managerEmail, subordinateEmail);
		return this.isSubordinateUser(managerEmail, subordinateEmail);
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