package ged.ejb.user;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
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

	public boolean emailExists(final String email) {
		Objects.requireNonNull(email);
		final boolean emailExists = this.userDao.isDuplicatedEmail(email);
		if (emailExists) {
			logger.debug("The email {} exists on database", email);
		}
		else {
			logger.debug("The email {} doesn't exists on database", email);
		}
		return emailExists;
	}

	public List<Role> findAllRoles() {
		return this.roleDao.findAll();
	}

	public Role findRoleByName(final String roleName) {
		Objects.requireNonNull(roleName);
		return this.roleDao.findRoleByName(roleName);
	}

	public List<User> findSubordinateUsers(final User user) {
		Objects.requireNonNull(user);
		logger.debug("Find subordinate users of user {}", user.getEmail());
		return this.userDao.findSubordinateUsers(user);
	}

	public User findUserByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find user by email {}", email);
		return this.userDao.findUserByEmail(email);
	}

	public List<User> findUsersOfflineLastMonth() {
		logger.debug("Find users offline last month");
		final LocalDateTime today = LocalDateTime.now();
		final LocalDateTime oneMonthAgo = today.minusMonths(1);
		return this.userDao.findUsersOffline(oneMonthAgo, today);
	}

	public List<User> findUsersOnlineLastWeek() {
		logger.debug("Find users online last week");
		final LocalDateTime today = LocalDateTime.now();
		final LocalDateTime oneWeekAgo = today.minusDays(7);
		return this.userDao.findUsersOnline(oneWeekAgo, today);
	}

	@Override
	protected AbstractDao<User> getDao() {
		return this.userDao;
	}

	private boolean hasValidManagerRole(final User user) {
		final User manager = user.getManager();
		if (manager != null) {
			if (!this.isASubordinateRole(manager.getRole(), user.getRole())) {
				return false;
			}
		}
		return true;
	}

	private boolean hasValidSubordinateRoles(final User user) {
		final List<User> subordinateUsers = this.findSubordinateUsers(user);
		for (final User subordinateUser : subordinateUsers) {
			if (!this.isASubordinateRole(user.getRole(), subordinateUser.getRole())) {
				return false;
			}
		}
		return true;
	}

	public boolean isASubordinateRole(final Role manager, final Role role) {
		Objects.requireNonNull(manager);
		Objects.requireNonNull(role);
		final List<Role> subordinateRoles = this.roleDao.findSubordinateRoles(manager);
		for (final Role subordinateRole : subordinateRoles) {
			if (subordinateRole.equals(role)) {
				logger.debug("Role {} is a subordinate role of {}", role.getName(), manager.getName());
				return true;
			}
		}
		logger.debug("Role {} isn't a subordinate role of {}", role.getName(), manager.getName());
		return false;
	}

	private boolean isDuplicateEmail(final User user) {
		final User repositoryUser = this.findUserByEmail(user.getEmail());
		return (repositoryUser != null) && !repositoryUser.equals(user);
	}

	public long numberOfUsersInTeam(final User user) {
		logger.debug("Find number of users in team of {}", user.getEmail());
		return this.userDao.numberOfUsersInTeam(user);
	}

	public long numberOfUsersOfflineLastMonth() {
		logger.debug("Find number of users offline last month");
		final LocalDateTime today = LocalDateTime.now();
		final LocalDateTime oneMonthAgo = today.minusMonths(1);
		return this.userDao.numberOfUsersOffline(oneMonthAgo, today);
	}

	public long numberOfUsersOnlineLastWeek() {
		logger.debug("Find number of users online last week");
		final LocalDateTime today = LocalDateTime.now();
		final LocalDateTime oneWeekAgo = today.minusDays(7);
		return this.userDao.numberOfUsersOnline(oneWeekAgo, today);
	}

	@Override
	public User save(final User user) {
		Objects.requireNonNull(user);
		if (user.equals(user.getManager())) {
			logger.warn("The user {} can't be his/her manager", user.getEmail());
			throw new IllegalStateException("User can't be his/her manager");
		}
		if (this.isDuplicateEmail(user)) {
			logger.warn("The email {} alredy in use", user.getEmail());
			throw new ValidationException("Email exists");
		}
		if (!this.hasValidSubordinateRoles(user)) {
			throw new IllegalStateException();
		}
		if (!this.hasValidManagerRole(user)) {
			throw new IllegalStateException();
		}
		return this.userDao.save(user);
	}

	public void setRoleDao(final RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}
}