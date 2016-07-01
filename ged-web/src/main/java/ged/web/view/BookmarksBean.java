package ged.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.bookmark.BookmarkService;
import ged.ejb.core.model.AuditedEntity;
import ged.web.core.view.AbstractPageBean;

@Named
@SessionScoped
public class BookmarksBean extends AbstractPageBean {

	private static final long serialVersionUID = 8786492354769335930L;

	private List<BookmarkDto> bookmarks;

	@Inject
	private transient BookmarkService bookmarkService;

	@Inject
	protected transient Logger logger;

	private List<String> urls;

	public void add(final AuditedEntity<Long> entity) {
		final String url = getUrl(entity);
		if (existsUrl(url)) {
			removeBookmark(url);
		} else {
			final Bookmark bookmark = createBookmark(entity);
			addBookmark(url, bookmark);
		}
	}

	private void addBookmark(final String url, final Bookmark bookmark) {
		if (this.bookmarks.size() > 9) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Bookmark full", "Bookmark full");
			return;
		}
		this.bookmarks.add(0, new BookmarkDto(bookmark.getText(), url));
		this.urls.add(0, url);
		this.bookmarkService.insert(bookmark);
	}

	private Bookmark createBookmark(final AuditedEntity<Long> entity) {
		final Bookmark bookmark = new Bookmark();
		bookmark.setEntityClass(entity.getClass().getSimpleName());
		bookmark.setEntityId(entity.getId());
		bookmark.setText(entity.toString());
		bookmark.setUser(this.sessionBean.getUser());
		return bookmark;
	}

	private boolean existsUrl(final String url) {
		return this.urls.contains(url);
	}

	public List<BookmarkDto> getBookmarks() {
		return this.bookmarks;
	}

	private String getUrl(final AuditedEntity<Long> entity) {
		final String contextPath = this.externalContext.getRequestContextPath();
		final String className = entity.getClass().getSimpleName().toLowerCase();
		final StringBuilder url = new StringBuilder();
		url.append(contextPath).append("/faces/").append(className).append("/").append(className).append("View.xhtml")
				.append("?id=").append(entity.getId());
		return url.toString();
	}

	private String getUrl(final Bookmark bookmark) {
		final String contextPath = this.externalContext.getRequestContextPath();
		final String className = bookmark.getEntityClass().toLowerCase();
		final StringBuilder url = new StringBuilder();
		url.append(contextPath).append("/faces/").append(className).append("/").append(className).append("View.xhtml")
				.append("?id=").append(bookmark.getEntityId());
		return url.toString();
	}

	@PostConstruct
	public void init() {
		final List<Bookmark> bookmarkEntities = this.bookmarkService.findAllByUser(this.sessionBean.getUser());
		this.urls = new ArrayList<>(bookmarkEntities.size());
		this.bookmarks = new ArrayList<>(bookmarkEntities.size());
		for (final Bookmark bookmark : bookmarkEntities) {
			final String url = getUrl(bookmark);
			final BookmarkDto bookmarkDto = new BookmarkDto(bookmark.getText(), url);
			this.bookmarks.add(bookmarkDto);
			this.urls.add(url);
		}
	}

	public boolean isBookmarked(final AuditedEntity<Long> entity) {
		final String url = getUrl(entity);
		return existsUrl(url);
	}

	public void remove(final AuditedEntity<Long> entity) {
		final String url = getUrl(entity);
		if (existsUrl(url)) {
			this.bookmarkService.delete(this.sessionBean.getUser(), entity.getClass().getSimpleName(), entity.getId());
			removeBookmark(url);
		}
	}

	private void removeBookmark(final String url) {
		final int index = this.urls.indexOf(url);
		this.bookmarks.remove(index);
		this.urls.remove(index);
	}
}