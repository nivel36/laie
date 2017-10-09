package ged.ejb.user;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class UserServiceImpl extends AbstratctAuditedService<User> implements UserService {

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
	protected Dao<User> getDao() {
		return this.userDao;
	}

	@Override
	protected void insert(final User user) {
		logger.debug("Insert user {}", user.getEmail());
		if (user.equals(user.getManager())) {
			logger.warn("The user {} can't be his/her manager", user.getEmail());
			throw new IllegalStateException("User can't be his/her manager");
		}
		this.userDao.insert(user);
	}

	private boolean isDeletingAdmin(final User user) {
		return user.getDeleted() != null && user.getDeleted() == true && user.isAdmin();
	}

	private boolean isLastAdminOnApp(final User user) {
		final User userInDataBase = find(user.getId());
		return userInDataBase.isAdmin() && !user.isAdmin() && !this.userDao.existsMoreThanOneAdmin();
	}

	@Override
	public List<User> searchByNameAndSurename(final String name, final String surename) {
		logger.debug("Search user by name {} and surename {}", new Object[] { name, surename });
		return this.userDao.searchByNameAndSurename(name, surename, false);
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