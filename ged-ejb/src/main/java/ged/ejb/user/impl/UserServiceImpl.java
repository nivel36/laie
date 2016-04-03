package ged.ejb.user.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.Repository;
import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.user.User;
import ged.ejb.user.UserDao;
import ged.ejb.user.UserService;

@Stateless
public class UserServiceImpl implements UserService {

	@Inject
	@Repository
	private UserDao userDao;

	@Override
	public void deleteBookmark(final Bookmark bookmark) {
		this.userDao.deleteBookmark(bookmark);
	}

	@Override
	public void deleteUser(final User user) {
		this.userDao.deleteUser(user);
	}

	private boolean existAdmin() {
		return this.userDao.countAdminRoles() > 1;
	}

	@Override
	public List<User> findAll() {
		return this.userDao.findAll();
	}

	@Override
	public Bookmark findBookmarkByUrl(final String url) {
		return this.userDao.findBookmarkByUrl(url);
	}

	@Override
	public User findById(final Long id) {
		return this.userDao.findById(id);
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
	public List<User> findUserTeam(final Long id) {
		return this.userDao.findUserTeam(id);
	}

	@Override
	public List<User> fullSearch(final String matching) {
		return this.userDao.fullSearch(matching);
	}

	@Override
	public List<User> fullSearch(final String name, final String surename) {
		return this.userDao.fullSearch(name, surename);
	}

	@Override
	public List<User> fullSearchByName(final String name) {
		return this.userDao.fullSearchByName(name);
	}

	@Override
	public List<User> fullSearchBySurename(final String surename) {
		return this.userDao.fullSearchBySurename(surename);
	}

	@Override
	public void insertBookmark(final Bookmark bookmark) {
		this.userDao.insertBookmark(bookmark);
	}

	@Override
	public void insertUser(final User user) {
		this.userDao.insertUser(user);
	}

	@Override
	public User updateUser(final User user) {
		if (existAdmin()) {
			return this.userDao.updateUser(user);
		} else {
			return user;
		}
	}
}
