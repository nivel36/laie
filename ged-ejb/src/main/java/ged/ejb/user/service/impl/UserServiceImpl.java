package ged.ejb.user.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.action.Audited;
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;
import ged.ejb.user.dao.UserDao;
import ged.ejb.user.service.UserService;

@Stateless
public class UserServiceImpl implements UserService {

	@Inject
	@Repository
	private UserDao userDao;

	@Override
	public void deleteUser(final User user) {
		user.setDeleted(true);
		this.userDao.updateUser(user);
	}

	@Override
	public boolean emailExists(final String email) {
		return this.userDao.emailExists(email);
	}

	private boolean existAdmin() {
		return this.userDao.countAdminRoles() > 1;
	}

	@Override
	public List<User> findAll() {
		return this.userDao.findAll();
	}

	@Override
	public User findById(final Long id) {
		return this.userDao.findById(id);
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
	@Audited
	public void insertUser(final User user) {
		this.userDao.insertUser(user);
	}

	@Override
	public void undeleteUser(final User user) {
		user.setDeleted(false);
		this.userDao.updateUser(user);
	}

	@Override
	public User updateUser(final User user) {
		if (existAdmin()) {
			return this.userDao.updateUser(user);
		} else {
			if (user.hasRole("ADMIN")) {
				return this.userDao.updateUser(user);
			}
			return user;
		}
	}
}
