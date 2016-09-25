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
		super();
		this.userDao = userDao;
	}

	@Override
	protected void doInsert(final User user) {
		if (user == null) {
			throw new NullPointerException();
		}
		UserServiceImpl.logger.log(Level.FINE, "INSERT user {0}", user.getFullName());
		if (user.equals(user.getManager())) {

			throw new IllegalStateException("User can't be his/her manager");
		}
		this.userDao.insert(user);
	}

	@Override
	protected User doUpdate(final User user) {
		if (existsMoreThanOneAdmin()) {
			return this.userDao.update(user);
		} else if (!user.hasRole("ADMIN")) {
			return this.userDao.update(user);
		} else {
			throw new UserException("Can not delete user");
		}
	}

	@Override
	public boolean emailExists(final String email) {
		return this.userDao.emailExists(email);
	}

	private boolean existsMoreThanOneAdmin() {
		return this.userDao.countAdminRoles() > 1;
	}

	@Override
	public List<User> findSubordinateUsers(final Long id) {
		return this.userDao.findSubordinateUsers(id);
	}

	@Override
	public User findUserByUsername(final String username) {
		return this.userDao.findUserByUsername(username);
	}

	@Override
	public Dao<Long, User> getDao() {
		return this.userDao;
	}

	@Override
	public List<User> searchByNameAndSurename(final String name, final String surename, final String email) {
		return this.userDao.searchByNameAndSurename(name, surename, email, false);
	}

	@Override
	public boolean usernameExists(final String username) {
		return this.userDao.usernameExists(username);
	}
}
