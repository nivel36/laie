package es.nivel36.laie.ejb.core.bookmark;

import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class BookmarkService {

	@Inject
	@Repository
	private UserDao userDao;

	public void addBookmark(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		final User user = bookmark.getUser();
		for (final Bookmark entityBookmark : user.getBookmarks()) {
			if (entityBookmark.getUrl().equals(bookmark.getUrl())) {
				return;
			}
		}
		userDao.update(user);
	}

	public void deleteBookmark(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		final User user = bookmark.getUser();
		user.getBookmarks().remove(bookmark);
		userDao.update(user);
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}
