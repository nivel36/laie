package ged.ejb.user;

import java.lang.invoke.MethodHandles;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class UserServiceImpl extends AbstractService<User> implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final UserDao userDao;

	@Inject
	public UserServiceImpl(@Repository final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}

	@Override
	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		final boolean emailExists = this.userDao.emailExists(email);
		if (emailExists) {
			logger.debug("The email {} exists on database", email);
		} else {
			logger.debug("The email {} doesn't exists on database", email);
		}
		return emailExists;
	}

	@Override
	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Find subordinate users of user {}", user.getEmail());
		return this.userDao.findSubordinateUsers(user);
	}

	@Override
	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find user by email {}", email);
		return this.userDao.findUserByEmail(email);
	}

	@Override
	public List<User> findUsersOfflineLastMonth() {
		logger.debug("Find users offline last month");
		final Date oneMonthAgo = getOneMonthAgo();
		final Date today = Calendar.getInstance().getTime();
		return this.userDao.findUsersOffline(oneMonthAgo, today);
	}

	@Override
	public List<User> findUsersOnlineLastWeek() {
		logger.debug("Find users online last week");
		final Date oneWeekAgo = getOneWeekAgo();
		final Date today = Calendar.getInstance().getTime();
		return this.userDao.findUsersOnline(oneWeekAgo, today);
	}

	@Override
	protected Dao<User> getDao() {
		return this.userDao;
	}

	private Date getOneMonthAgo() {
		final Date referenceDate = new Date();
		final Calendar c = Calendar.getInstance();
		c.setTime(referenceDate);
		c.add(Calendar.MONTH, -30);
		return c.getTime();
	}

	private Date getOneWeekAgo() {
		final Date referenceDate = new Date();
		final Calendar c = Calendar.getInstance();
		c.setTime(referenceDate);
		c.add(Calendar.MONTH, -7);
		return c.getTime();
	}

	@Override
	protected void insert(final User user) {
		logger.debug("Insert user {}", user.getEmail());
		if (user.equals(user.getManager())) {
			logger.warn("The user {} can't be his/her manager", user.getEmail());
			throw new IllegalStateException("User can't be his/her manager");
		}
		if (findUserByEmail(user.getEmail()) != null) {
			throw new ValidationException("Email exists");
		}
		if (user.getLanguage() == null) {
			user.setLanguage("ES");
		}
		if (user.getRowsPerPage() == 0) {
			user.setRowsPerPage(25);
		}
		if (user.getPassword() == null || user.getPassword().length == 0) {
			user.setPassword("M+SzETkPtT+deVQNIScBEXivvfozSne5QqIqyWICLv0=".toCharArray());
		}
		this.userDao.insert(user);
	}

	private boolean isDeletingAdmin(final User user) {
		return user.isDeleted() && user.isAdmin();
	}

	private boolean isLastAdminOnApp(final User user) {
		final User userInDataBase = find(user.getId());
		return userInDataBase.isAdmin() && !user.isAdmin() && !this.userDao.existsMoreThanOneAdmin();
	}

	@Override
	public long numberOfUsersInTeam(final User user) {
		logger.debug("Find number of users in team of {}", user.getEmail());
		return this.userDao.numberOfUsersInTeam(user);
	}

	@Override
	public long numberOfUsersOfflineLastMonth() {
		logger.debug("Find number of users offline last month");
		final Date oneMonthAgo = getOneMonthAgo();
		final Date today = Calendar.getInstance().getTime();
		return this.userDao.numberOfUsersOffline(oneMonthAgo, today);
	}

	@Override
	public long numberOfUsersOnlineLastWeek() {
		logger.debug("Find number of users online last week");
		final Date oneWeekAgo = getOneWeekAgo();
		final Date today = Calendar.getInstance().getTime();
		return this.userDao.numberOfUsersOnline(oneWeekAgo, today);
	}

	@Override
	protected User update(final User user) {
		if (isLastAdminOnApp(user)) {
			logger.warn("Can't change user {} role. Last Admin on app", user.getEmail());
			throw new UserException("Can't change user role. Last Admin on app");
		}
		if (isDeletingAdmin(user)) {
			logger.warn("Can't delete user {}. User is Admin", user.getEmail());
			throw new UserException("Can't delete user. User is Admin");
		}
		logger.debug("Update user {}", user.getEmail());
		return this.userDao.update(user);
	}
}