package es.nivel36.laie.ejb.core.bookmark;

import java.util.List;
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
	private BookmarkDao bookmarkDao;

	@Inject
	@Repository
	private UserDao userDao;

	private BookmarkMapper bookmarkMaper = new BookmarkMapper();

	public void addBookmark(final BookmarkDto bookmark, final String userUid) {
		Objects.requireNonNull(bookmark);
		Objects.requireNonNull(userUid);
		final User user = this.userDao.findUserByUid(userUid);
		final List<Bookmark> usersBookmarks = this.bookmarkDao.findByUserUid(userUid);

		for (final Bookmark entityBookmark : usersBookmarks) {
			if (entityBookmark.getUrl().equals(bookmark.getUrl())) {
				return;
			}
		}

		final Bookmark entity = new Bookmark();
		entity.setUser(user);
		entity.setImageUrl(bookmark.getImageUrl());
		entity.setTitle(bookmark.getTitle());
		entity.setUrl(bookmark.getUrl());
		bookmarkDao.insert(entity);
	}

	public void deleteBookmark(final BookmarkDto bookmark, final String userUid) {
		Objects.requireNonNull(bookmark);
		Objects.requireNonNull(userUid);
		final List<Bookmark> usersBookmarks = this.bookmarkDao.findByUserUid(userUid);
		for (final Bookmark entityBookmark : usersBookmarks) {
			if (entityBookmark.getUrl().equals(bookmark.getUrl())) {
				this.bookmarkDao.delete(entityBookmark);
			}
		}
	}

	public List<BookmarkDto> findBookmarksByUserUid(String userUid) {
		Objects.requireNonNull(userUid);
		final List<Bookmark> bookmarks = this.bookmarkDao.findByUserUid(userUid);
		return bookmarkMaper.mapList(bookmarks);
	}

	public void setBookmarkDao(final BookmarkDao bookmarkDao) {
		Objects.requireNonNull(bookmarkDao);
		this.bookmarkDao = bookmarkDao;
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}
