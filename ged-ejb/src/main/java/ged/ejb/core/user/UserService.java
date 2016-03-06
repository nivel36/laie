package ged.ejb.core.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.GenericServiceImpl;
import ged.ejb.core.bookmark.Bookmark;

@Stateless
public class UserService extends GenericServiceImpl {

	@Inject
	private UserDao userDao;

	public List<User> findAll() {
		return this.userDao.findAll();
	}

	public Bookmark findBookmarkByUrl(final String url) {
		return this.userDao.findBookmarkByUrl(url);
	}

	public User findById(final Long id) {
		return this.userDao.findById(id);
	}

	public List<User> findUsers(final String name, final String surenames) {
		return this.userDao.findUsers(name, surenames);
	}

	public void insertBookmark(final Bookmark bookmark) {
		this.userDao.insertBookmark(bookmark);
	}

	public void insertUser(final User user) {
		this.userDao.insertUser(user);
	}

	public void removeBookmark(final Bookmark bookmark) {
		this.userDao.removeBookmark(bookmark);
	}

	public void updateUser(final User user) {
		this.userDao.updateUser(user);
	}
}
