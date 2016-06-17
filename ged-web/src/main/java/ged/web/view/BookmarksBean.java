package ged.web.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.BookmarkService;
import ged.web.core.view.AbstractPageBean;

@Named
@SessionScoped
public class BookmarksBean extends AbstractPageBean {

	private static final long serialVersionUID = 8786492354769335930L;

	private List<Bookmark> bookmarks;

	@Inject
	private transient BookmarkService bookmarkService;

	private List<String> urls;

	public void add(final AuditedEntity entity) {
		final String url = getUrl(entity);
		if (existsUrl(url)) {
			removeBookmark(url);
		} else {
			final Bookmark bookmark = createBookmark(entity);
			addBookmark(bookmark);
		}
	}

	private void addBookmark(final Bookmark bookmark) {
		if (this.bookmarks.size() > 9) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Bookmark full", "Bookmark full");
			return;
		}
		this.bookmarks.add(0, bookmark);
		this.urls.add(0, bookmark.getUrl());
		this.bookmarkService.insert(bookmark);
	}

	private Bookmark createBookmark(final AuditedEntity entity) {
		final Bookmark bookmark = new Bookmark();
		final String url = getUrl(entity);
		bookmark.setUrl(url);
		bookmark.setText(entity.toString());
		bookmark.setUser(this.sessionBean.getUser());
		return bookmark;
	}

	private boolean existsUrl(final String url) {
		return this.urls.contains(url);
	}

	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	private String getUrl(final AuditedEntity entity) {
		final String contextPath = this.externalContext.getRequestContextPath();
		final String viewId = this.facesContext.getViewRoot().getViewId();
		final StringBuilder url = new StringBuilder();
		url.append(contextPath).append(viewId).append("?id=").append(entity.getId());
		return url.toString();
	}

	@PostConstruct
	public void init() {
		this.bookmarks = this.bookmarkService.findAllByUser(this.sessionBean.getUser());
		this.urls = new ArrayList<>(this.bookmarks.size());
		for (final Bookmark bookmark : this.bookmarks) {
			this.urls.add(bookmark.getUrl());
		}
	}

	public boolean isBookmarked(final AuditedEntity entity) {
		final String url = getUrl(entity);
		return existsUrl(url);
	}

	public void remove(final AuditedEntity entity) {
		final String url = getUrl(entity);
		if (existsUrl(url)) {
			this.bookmarkService.delete(url);
		}
	}

	public void removeBookmark(final String url) {
		final int index = this.urls.indexOf(url);
		this.bookmarks.remove(index);
		this.urls.remove(index);
	}

	public void setUserService(final BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}
}
