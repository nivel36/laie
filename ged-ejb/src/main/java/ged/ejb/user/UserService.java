package ged.ejb.user;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.security.Securized;
import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleDao;

@Stateless
public class UserService extends AbstractAuditedService<User> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private RoleDao roleDao;

	@Inject
	@Repository
	private UserDao userDao;

	public List<Role> findAllRoles() {
		return this.roleDao.findAll();
	}

	public Role findRoleByName(final String roleName) {
		Objects.requireNonNull(roleName);
		logger.debug("Finding role by name {}", roleName);
		return this.roleDao.findRoleByName(roleName);
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Finding subordinate users of user {}", user.getEmail());
		return this.userDao.findSubordinateUsers(user);
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Finding user by email {}", email);
		return this.userDao.findUserByEmail(email);
	}

	public List<User> findUsersOfflineLastMonth() {
		logger.debug("Finding users offline last month");
		final LocalDateTime today = LocalDateTime.now();
		final LocalDateTime oneMonthAgo = today.minusMonths(1);
		return this.userDao.findUsersOffline(oneMonthAgo, today);
	}

	public List<User> findUsersOnlineLastWeek() {
		logger.debug("Finding users online last week");
		final LocalDateTime today = LocalDateTime.now();
		final LocalDateTime oneWeekAgo = today.minusDays(7);
		return this.userDao.findUsersOnline(oneWeekAgo, today);
	}

	@Override
	protected AbstractDao<User> getDao() {
		return this.userDao;
	}

	private boolean hasValidManager(final User user) {
		final User manager = user.getManager();
		if (user.isAdmin() && (manager != null)) {
			return false;
		}
		return true;
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
		return findSubordinateUsers(manager).contains(subordinate);
	}

	@Override
	@Securized
	public User save(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Saving user {}", user.getEmail());
		if (user.equals(user.getManager())) {
			logger.warn("The user {} can't be his/her manager", user.getEmail());
			throw new IllegalStateException("User can't be his/her manager");
		}
		if (!this.hasValidManager(user)) {
			logger.warn("The user {} has an admin role but has {} as a manager", user.getEmail(), user.getManager().getRole());
			throw new IllegalStateException();
		}
		return super.save(user);
	}

	public void setRoleDao(final RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}
}