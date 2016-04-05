package ged.web.core.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.bookmark.BookmarkFullExpcetion;
import ged.ejb.core.model.Action;
import ged.ejb.core.model.SavedSearch;
import ged.ejb.user.User;

@Named
@SessionScoped
public class SessionBean extends AbstractBean {

	private static final long serialVersionUID = -8079836415042166193L;

	@Produces
	private List<Action> actions = new ArrayList<Action>();

	private List<Bookmark> bookmarks = new ArrayList<Bookmark>();

	private Locale locale;

	@Produces
	private List<SavedSearch> savedSearches = new ArrayList<SavedSearch>();

	@Produces
	private User user;

	public void addAction(final Action action) {
		if (!containsAction(action)) {
			if (this.actions.size() > 9) {
				this.actions.remove(9);
			}
			this.actions.add(0, action);
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

	public boolean containsAction(final Action action) {
		return this.actions.contains(action);
	}

	public boolean containsBookmark(final Bookmark bookmark) {
		return this.bookmarks.contains(bookmark);
	}

	public List<Action> getActions() {
		return this.actions;
	}

	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public int getRowsPerPage() {
		return this.user.getRowsPerPage();
	}

	public List<SavedSearch> getSavedSearches() {
		return this.savedSearches;
	}

	public User getUser() {
		return this.user;
	}

	@PostConstruct
	public void init() {
		this.locale = this.facesContext.getApplication().getDefaultLocale();
	}

	public void removeBookmark(final Bookmark bookmark) {
		this.bookmarks.remove(bookmark);
	}

	public void setActions(final List<Action> actions) {
		this.actions = actions;
	}

	public void setBookmarks(final List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public void setRowsPerPage(final int rowsPerPage) {
		this.user.setRowsPerPage(rowsPerPage);
	}

	public void setSavedSearches(final List<SavedSearch> savedSearches) {
		this.savedSearches = savedSearches;
	}

	public void setUser(final User user) {
		this.user = user;
		this.locale = new Locale(this.user.getLanguage());
	}
}
