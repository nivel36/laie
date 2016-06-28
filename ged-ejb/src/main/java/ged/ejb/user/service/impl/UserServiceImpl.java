package ged.ejb.user.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedCrudService;
import ged.ejb.core.action.Action;
import ged.ejb.core.action.Audited;
import ged.ejb.core.model.CrudDao;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;
import ged.ejb.user.dao.UserDao;
import ged.ejb.user.service.UserService;

@Stateless
public class UserServiceImpl extends AbstratctAuditedCrudService<User> implements UserService {

	@Inject
	@Repository
	private UserDao userDao;

	@Override
	public boolean emailExists(final String email) {
		return this.userDao.emailExists(email);
	}

	private boolean existAdmin() {
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
	public List<User> findUsers(final String name, final String surenames) {
		return this.userDao.findUsers(name, surenames);
	}

	@Override
	public List<User> fullSearch(final String name, final String surename, final String userEmail) {
		return this.userDao.fullSearch(name, surename, userEmail, false);
	}

	@Override
	public CrudDao<Long, User> getDao() {
		return this.userDao;
	}

	@Override
	@Audited(action = Action.INSERT)
	public void insert(final User user) {
		if (user == null) {
			throw new NullPointerException();
		}
		if (user.getManager().equals(user)) {
			throw new IllegalStateException("User can't be his manager");
		}
		insert(user);
	}

	@Override
	@Audited(action = Action.UPDATE)
	public User update(final User user) {
		if (existAdmin()) {
			return this.userDao.update(user);
		} else {
			if (user.hasRole("ADMIN")) {
				return this.userDao.update(user);
			}
			return user;
		}
	}
}
