package ged.web.view;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.bookmark.BookmarkFullExpcetion;
import ged.ejb.core.model.AuditedEntity;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractPageBean;

@Named
@RequestScoped
public class BookmarksBean extends AbstractPageBean {

	private static final long serialVersionUID = 8786492354769335930L;

	@Inject
	private UserService userService;

	public void add(final AuditedEntity entity) {
		final Bookmark bookmark = createBookmark(entity);
		try {
			if (this.sessionBean.containsBookmark(bookmark)) {
				this.sessionBean.removeBookmark(bookmark);
				this.sessionBean.addBookmark(bookmark);
			} else {
				this.sessionBean.addBookmark(bookmark);
				this.userService.insertBookmark(bookmark);
			}
		} catch (final BookmarkFullExpcetion e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Bookmark full", "Bookmark full");
		}
	}

	private Bookmark createBookmark(final AuditedEntity entity) {
		final Bookmark bookmark = new Bookmark();
		final String url = getUrl(entity);
		bookmark.setUrl(url);
		bookmark.setText(entity.toString());
		bookmark.setUser(this.sessionBean.getUser());
		return bookmark;
	}

	private String getUrl(final AuditedEntity entity) {
		final String contextPath = this.facesContext.getExternalContext().getRequestContextPath();
		final String viewId = this.facesContext.getViewRoot().getViewId();
		final String url = contextPath + viewId + "?id=" + entity.getId();
		return url;
	}

	public boolean isBookmarked(final AuditedEntity entity) {
		final Bookmark bookmark = createBookmark(entity);
		return this.sessionBean.containsBookmark(bookmark);
	}

	public void remove(final AuditedEntity entity) {
		final String url = getUrl(entity);
		final Bookmark bookmark = this.userService.findBookmarkByUrl(url);
		if (this.sessionBean.containsBookmark(bookmark)) {
			this.sessionBean.removeBookmark(bookmark);
			this.userService.deleteBookmark(bookmark);
		}
	}
}
