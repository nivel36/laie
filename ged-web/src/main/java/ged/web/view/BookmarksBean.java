package ged.web.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.bookmark.BookmarkFullExpcetion;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.BookmarkService;
import ged.web.core.view.AbstractPageBean;

@Named
@SessionScoped
public class BookmarksBean extends AbstractPageBean {

	private static final long serialVersionUID = 8786492354769335930L;

	private List<Bookmark> bookmarks;

	private BookmarkService bookmarkService;

	public void add(final AuditedEntity entity) {
		final Bookmark bookmark = createBookmark(entity);
		try {
			if (containsBookmark(bookmark)) {
				removeBookmark(bookmark);
				addBookmark(bookmark);
			} else {
				addBookmark(bookmark);
				this.bookmarkService.insert(bookmark);
			}
		} catch (final BookmarkFullExpcetion e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Bookmark full", "Bookmark full");
		}
	}

	public void addBookmark(final Bookmark bookmark) throws BookmarkFullExpcetion {
		if (!containsBookmark(bookmark)) {
			if (this.bookmarks.size() > 9) {
				throw new BookmarkFullExpcetion();
			}
			this.bookmarks.add(0, bookmark);
		}
	}

	public boolean containsBookmark(final Bookmark bookmark) {
		return this.bookmarks.contains(bookmark);
	}

	private Bookmark createBookmark(final AuditedEntity entity) {
		final Bookmark bookmark = new Bookmark();
		final String url = getUrl(entity);
		bookmark.setUrl(url);
		bookmark.setText(entity.toString());
		bookmark.setUser(this.sessionBean.getUser());
		return bookmark;
	}

	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	private String getUrl(final AuditedEntity entity) {
		final String contextPath = this.facesContext.getExternalContext().getRequestContextPath();
		final String viewId = this.facesContext.getViewRoot().getViewId();
		final String url = contextPath + viewId + "?id=" + entity.getId();
		return url;
	}

	@PostConstruct
	public void init() {
		this.bookmarks = this.bookmarkService.findAll();
	}

	public boolean isBookmarked(final AuditedEntity entity) {
		final Bookmark bookmark = createBookmark(entity);
		return containsBookmark(bookmark);
	}

	public void remove(final AuditedEntity entity) {
		final String url = getUrl(entity);
		final Bookmark bookmark = this.bookmarkService.findByUrl(url);
		if (containsBookmark(bookmark)) {
			removeBookmark(bookmark);
			this.bookmarkService.delete(bookmark);
		}
	}

	public void removeBookmark(final Bookmark bookmark) {
		this.bookmarks.remove(bookmark);
	}

	public void setBookmarks(final List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	@Inject
	public void setUserService(final BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}
}
