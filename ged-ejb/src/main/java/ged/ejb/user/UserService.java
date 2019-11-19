package ged.ejb.user;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Stateless
public class UserService extends AbstractService<User> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private UserDao userDao;

	public void changePassword(final User user, final String newPassword) {
		user.getCredential().setPassword(newPassword);
		this.userDao.save(user);
	}

	public JobOffer findByUid(final String uid) {
		Objects.requireNonNull(uid);
		return this.userDao.findByUid(uid);
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Finding subordinate users of user {}", user.getEmail());
		return this.userDao.findSubordinateUsers(user);
	}
	
	public User findUserAndCredential(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Finding user credential for user {}", email);
		return this.userDao.findUserAndCredential(email);
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Finding user by email {}", email);
		return this.userDao.findUserByEmail(email);
	}

	@Override
	protected AbstractDao<User> getDao() {
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
		final User user = this.userDao.findUserAndCredential(email);
		if (user == null) {
			throw new LoginException("Invalid email");
		}
		final Credential credential = user.getCredential();
		if (!credential.isValid(password)) {
			throw new LoginException("Passwords doesn't match");
		}
		user.setLastConnection(LocalDateTime.now());
		return this.userDao.save(user);
	}

	@Override
	public User save(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Saving user {}", user.getEmail());
		this.validateManager(user);
		return super.save(user);
	}

	public void setUserDao(final UserDao userDao) {
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