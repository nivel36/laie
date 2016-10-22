package ged.ejb.user;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class UserServiceImpl extends AbstratctAuditedService<User> implements UserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

	private final UserDao userDao;

	@Inject
	public UserServiceImpl(@Repository final UserDao userDao) {
		if (userDao == null) {
			throw new NullPointerException("userDao");
		}
		this.userDao = userDao;
	}

	@Override
	protected void doInsert(final User user) {
		if (user == null) {
			throw new NullPointerException();
		}
		logger.log(Level.FINE, "INSERT user {0}", user.getFullName());
		if (user.equals(user.getManager())) {
			logger.log(Level.WARNING, "The user {0} can't be his/her manager", user.getFullName());
			throw new IllegalStateException("User can't be his/her manager");
		}
		this.userDao.insert(user);
	}

	@Override
	protected User doUpdate(final User user) {
		if (user == null) {
			throw new NullPointerException("user");
		}
		if (!user.hasRole("ADMIN") || this.userDao.existsMoreThanOneAdmin()) {
			logger.log(Level.FINE, "UPDATE user {0}", user.getFullName());
			return this.userDao.update(user);
		} else {
			logger.log(Level.WARNING, "Can't delete user. Last Admin on app");
			throw new UserException("Can't delete user. Last Admin on app");
		}
	}

	@Override
	public boolean emailExists(final String email) {
		if (email == null) {
			throw new NullPointerException("email");
		}
		final boolean emailExists = this.userDao.emailExists(email);
		if (emailExists) {
			logger.log(Level.FINE, "The email {} exists on database", email);
		} else {
			logger.log(Level.FINE, "The email {} doesn't exists on database", email);
		}
		return emailExists;
	}

	@Override
	public List<User> findSubordinateUsers(final Long id) {
		if (id == null) {
			throw new NullPointerException("id");
		}
		logger.log(Level.FINE, "FIND subordinate users by id {}", id);
		return this.userDao.findSubordinateUsers(id);
	}

	@Override
	public User findUserByUsername(final String username) {
		if (username == null) {
			throw new NullPointerException("username");
		}
		logger.log(Level.FINE, "FIND user by username {}", username);
		return this.userDao.findUserByUsername(username);
	}

	@Override
	protected Dao<Long, User> getDao() {
		return this.userDao;
	}

	@Override
	public List<User> searchByNameAndSurename(final String name, final String surename, final String email) {
		logger.log(Level.FINE, "SEARCH user by name {} and surename {}, removing users with email {}",
				new Object[] { name, surename, email });
		return this.userDao.searchByNameAndSurename(name, surename, email, false);
	}

	@Override
	public boolean usernameExists(final String username) {
		if (username == null) {
			throw new NullPointerException("username");
		}
		final boolean usernameExists = this.userDao.usernameExists(username);
		if (usernameExists) {
			logger.log(Level.FINE, "The username {} exists on database", username);
		} else {
			logger.log(Level.FINE, "The username {} doesn't exists on database", username);
		}
		return usernameExists;
	}
}
