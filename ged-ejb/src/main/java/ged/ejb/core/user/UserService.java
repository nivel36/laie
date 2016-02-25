package ged.ejb.core.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.GenericServiceImpl;

@Stateless
public class UserService extends GenericServiceImpl {

	@Inject
	private UserDao userDao;

	public List<User> findAll() {
		return this.userDao.findAll();
	}

	public User findById(final Long id) {
		return this.userDao.findById(id);
	}

	public List<User> findUsers(final String name, final String surenames) {
		return this.userDao.findUsers(name, surenames);
	}
}
