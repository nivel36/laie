package es.nivel36.laie.ejb.core.bookmark;

import java.util.Objects;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class BookmarkService {

	@Inject

	private UserDao userDao;

	@Inject

	private BookmarkDao bookmarkDao;

	public User addBookmark(final Bookmark bookmark, final User user) {
		Objects.requireNonNull(bookmark);
		for (final Bookmark userBookmark : user.getBookmarks()) {
			if (userBookmark.getUrl().equals(bookmark.getUrl())) {
				return user;
			}
		}
		final Bookmark entity = bookmarkDao.findBookmarkByUrl(bookmark.getUrl());
		if (entity != null) {
			user.getBookmarks().add(entity);
		} else {
			user.getBookmarks().add(bookmark);
		}
		return userDao.update(user);
	}

	public User deleteBookmark(final Bookmark bookmark, final User user) {
		Objects.requireNonNull(bookmark);
		user.getBookmarks().remove(bookmark);
		return userDao.update(user);
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}
