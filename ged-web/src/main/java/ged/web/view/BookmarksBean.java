package ged.web.view;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.bookmark.BookmarkService;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.User;
import ged.web.core.view.AbstractPageBean;

@Named
@SessionScoped
public class BookmarksBean extends AbstractPageBean {

	private static final transient Logger logger = Logger.getLogger(BookmarksBean.class.getName());

	private static final long serialVersionUID = 8786492354769335930L;

	private List<Bookmark> bookmarks;

	private final transient BookmarkService bookmarkService;

	@Inject
	public BookmarksBean(final BookmarkService bookmarkService) {
		Objects.requireNonNull(bookmarkService);
		this.bookmarkService = bookmarkService;
	}

	public void add(final AuditedEntity<Long> entity) {
		BookmarksBean.logger.log(Level.FINE, "Adding bookmark {} for user {}",
				new Object[] { entity, this.sessionBean.getUser().getUsername() });
		final Bookmark bookmark = createBookmark(entity);
		if (this.bookmarks.size() > 9) {
			BookmarksBean.logger.log(Level.WARNING, "Bookmark full for user {}",
					this.sessionBean.getUser().getUsername());
			addMessage(FacesMessage.SEVERITY_ERROR, "Bookmark full", "Bookmark full");
			return;
		}
		this.bookmarkService.insert(bookmark);
		this.bookmarks.add(bookmark);
	}

	private String buildUrl(final String className) {
		final StringBuilder sb = new StringBuilder();
		sb.append("/faces/").append(className).append("/").append(className).append("View.xhtml").append("?id=");
		return sb.toString();
	}

	private Bookmark createBookmark(final AuditedEntity<Long> entity) {
		final Bookmark bookmark = new Bookmark();
		bookmark.setEntityClass(entity.getClass().getSimpleName());
		bookmark.setEntityId(entity.getId());
		bookmark.setText(entity.toString());
		bookmark.setUser(this.sessionBean.getUser());
		return bookmark;
	}

	private void findAllBookmarks() {
		this.bookmarks = this.bookmarkService.findAllByUser(this.sessionBean.getUser());
	}

	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	private String getUrl(final Bookmark bookmark) {
		final String entityClass = bookmark.getEntityClass();
		final String className = entityClass.substring(0, 1).toLowerCase() + entityClass.substring(1);
		return buildUrl(className) + bookmark.getEntityId();
	}

	public String go(final Bookmark bookmark) {
		final String url = getUrl(bookmark);
		return url + "&faces-redirect=true";
	}

	@PostConstruct
	public void init() {
		findAllBookmarks();
	}

	public boolean isBookmarked(final AuditedEntity<Long> entity) {
		final Bookmark bookmark = createBookmark(entity);
		return this.bookmarks.contains(bookmark);
	}

	public void remove(final AuditedEntity<Long> entity) {
		final User user = this.sessionBean.getUser();
		final String className = entity.getClass().getSimpleName();
		final Long id = entity.getId();
		this.bookmarkService.delete(user, className, id);
		findAllBookmarks();
	}
}