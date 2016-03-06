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
	public void insertBookmark(final Bookmark bookmark) {
		this.userDao.insertBookmark(bookmark);
	}

	@Override
	public void insertUser(final User user) {
		this.userDao.insertUser(user);
	}

	@Override
	public User updateUser(final User user) {
		return this.userDao.updateUser(user);
	}
}
