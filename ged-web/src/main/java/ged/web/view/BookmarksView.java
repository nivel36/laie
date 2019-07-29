package ged.web.view;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.bookmark.BookmarkService;
import ged.ejb.core.model.AbstractEntity;
import ged.ejb.user.User;
import ged.web.core.util.Message;
import ged.web.core.view.AbstractView;

@Named
@SessionScoped
public class BookmarksView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 8786492354769335930L;

	private List<Bookmark> bookmarks;

	@Inject
	private transient BookmarkService bookmarkService;

	public void add(final AbstractEntity entity) {
		logger.debug("Adding bookmark {} for user {}", entity, this.sessionUser.get().getEmail());
		Bookmark bookmark = this.createBookmark(entity);
		if (this.bookmarks.size() > 9) {
			logger.warn("Bookmark full for user {}", this.sessionUser.get().getEmail());
			Message.addError("Bookmark full", "Bookmark full");
			return;
		}
		bookmark = this.bookmarkService.save(bookmark);
		this.bookmarks.add(bookmark);
	}

	private String buildUrl(final String className) {
		final StringBuilder sb = new StringBuilder();
		sb.append("/").append(className).append("/").append(className).append("View.xhtml").append("?id=");
		return sb.toString();
	}

	private Bookmark createBookmark(final AbstractEntity entity) {
		final Bookmark bookmark = new Bookmark();
		bookmark.setEntityClass(entity.getClass().getSimpleName());
		bookmark.setEntityId(entity.getId());
		bookmark.setText(entity.toString());
		bookmark.setUser(this.sessionUser.get());
		return bookmark;
	}

	private void findAllBookmarks() {
		this.bookmarks = this.bookmarkService.findAllByUser(this.sessionUser.get());
	}

	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	private String getUrl(final Bookmark bookmark) {
		final String entityClass = bookmark.getEntityClass();
		final String className = entityClass.substring(0, 1).toLowerCase() + entityClass.substring(1);
		return this.buildUrl(className) + bookmark.getEntityId();
	}

	public String go(final Bookmark bookmark) {
		final String url = this.getUrl(bookmark);
		return url + "&faces-redirect=true";
	}

	@PostConstruct
	public void init() {
		this.findAllBookmarks();
	}

	public boolean isBookmarked(final AbstractEntity entity) {
		final Bookmark bookmark = this.createBookmark(entity);
		return this.bookmarks.contains(bookmark);
	}

	public void remove(final AbstractEntity entity) {
		final User user = this.sessionUser.get();
		final String className = entity.getClass().getSimpleName();
		final long id = entity.getId();
		this.bookmarkService.delete(user, className, id);
		this.findAllBookmarks();
	}

	public void setBookmarkService(final BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}
}