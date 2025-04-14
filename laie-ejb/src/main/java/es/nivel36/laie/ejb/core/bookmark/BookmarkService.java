package es.nivel36.laie.ejb.core.bookmark;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 * Provides services for managing bookmarks associated with users.
 *
 * @see Bookmark
 */
@Stateless
public class BookmarkService {

	private static final Logger logger = LoggerFactory.getLogger(BookmarkService.class);

	private @Inject UserDao userDao;
	private @Inject BookmarkDao bookmarkDao;

	/**
	 * Adds a bookmark to the specified user's collection.
	 *
	 * @param bookmark the bookmark to be added; must not be null
	 * @param user     the user to whom the bookmark will be added; must not be null
	 * @return the updated user with the new bookmark added, or the original user if
	 *         the bookmark already exists
	 * @throws NullPointerException if either bookmark or user is null
	 */
	public User addBookmark(final Bookmark bookmark, final User user) {
		Objects.requireNonNull(bookmark);
		Objects.requireNonNull(user);
		logger.debug("Adding bookmark {} to user {}", bookmark, user);

		if (user.getBookmarks().contains(bookmark)) {
			return user;
		}

		Bookmark bookmarkToAdd = bookmarkDao.findBookmarkByUrl(bookmark.getUrl());
		if (bookmarkToAdd == null) {
			bookmarkToAdd = bookmark;
		}

		user.addBookmark(bookmarkToAdd);
		logger.trace("Bookmark {} added successfully to user {}", bookmark, user);
		return userDao.update(user);
	}

	/**
	 * Removes a bookmark from the specified user's collection.
	 *
	 * @param bookmark the bookmark to be removed; must not be null
	 * @param user     the user from whom the bookmark will be removed; must not be
	 *                 null
	 * @return the updated user after removing the bookmark
	 * @throws NullPointerException if either bookmark or user is null
	 */
	public User deleteBookmark(final Bookmark bookmark, final User user) {
		Objects.requireNonNull(bookmark);
		Objects.requireNonNull(user);
		logger.debug("Removing bookmark {} from user {}", bookmark, user);

		user.removeBookmark(bookmark);
		logger.trace("Bookmark {} removed successfully from user {}", bookmark, user);
		return userDao.update(user);
	}

	/**
	 * Sets the user DAO used for updating user entities.
	 *
	 * @param userDao the UserDao instance to set; must not be null
	 * @throws NullPointerException if the provided userDao is null
	 */
	public void setUserDao(final UserDao userDao) {
		this.userDao = Objects.requireNonNull(userDao);
	}

	/**
	 * Sets the bookmark DAO used for managing bookmark entities
	 *
	 * @param bookamarkDao the BookamarkDao instance to set; must not be null
	 * @throws NullPointerException if the provided bookamarkDao is null
	 */
	public void setBookmarkDao(BookmarkDao bookmarkDao) {
		this.bookmarkDao = Objects.requireNonNull(bookmarkDao);
	}
}
