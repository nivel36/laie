package ged.ejb.core.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.GenericServiceImpl;

@Stateless
public class UserService extends GenericServiceImpl {

	@Inject
	private UserDao userDao;
	
	public List<User> findUsers(String name, String surenames) {
		return userDao.findUsers(name, surenames);
	}
	
	public List<User> findAll() {
		return userDao.findAll();
	}
}
