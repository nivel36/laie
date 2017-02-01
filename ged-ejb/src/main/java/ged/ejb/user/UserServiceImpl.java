package ged.ejb.user;

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
public class UserServiceImpl extends AbstratctAuditedService<Long, User> implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

	private final UserDao userDao;

	@Inject
	public UserServiceImpl(@Repository final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}

	@Override
	protected void doInsert(final User user) {
		logger.debug("INSERT user {}", user.getFullName());
		if (user.equals(user.getManager())) {
			logger.warn("The user {} can't be his/her manager", user.getFullName());
			throw new IllegalStateException("User can't be his/her manager");
		}
		this.userDao.insert(user);
	}

	@Override
	protected User doUpdate(final User user) {
		if (!user.hasRole("ADMIN") || this.userDao.existsMoreThanOneAdmin()) {
			logger.debug("UPDATE user {}", user.getFullName());
			return this.userDao.update(user);
		} else {
			logger.warn("Can't delete user. Last Admin on app");
			throw new UserException("Can't delete user. Last Admin on app");
		}
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
	public List<User> findSubordinateUsers(final Long id) {
		Objects.requireNonNull(id);
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		logger.debug("FIND subordinate users by id {}", id);
		return this.userDao.findSubordinateUsers(id);
	}

	@Override
	public User findUserByUsername(final String username) {
		Objects.requireNonNull(username);
		logger.debug("FIND user by username {}", username);
		return this.userDao.findUserByUsername(username);
	}

	@Override
	protected Dao<Long, User> getDao() {
		return this.userDao;
	}

	@Override
	public List<User> searchByNameAndSurename(final String name, final String surename) {
		logger.debug("SEARCH user by name {} and surename {}", new Object[] { name, surename });
		return this.userDao.searchByNameAndSurename(name, surename, false);
	}

	@Override
	public boolean usernameExists(final String username) {
		Objects.requireNonNull(username);
		final boolean usernameExists = this.userDao.usernameExists(username);
		if (usernameExists) {
			logger.debug("The username {} exists on database", username);
		} else {
			logger.debug("The username {} doesn't exists on database", username);
		}
		return usernameExists;
	}
}
