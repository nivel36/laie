package ged.web.core.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import ged.ejb.core.bookmark.Bookmark;
import ged.ejb.core.model.Action;
import ged.ejb.core.model.SavedSearch;
import ged.ejb.core.user.User;
import ged.ejb.core.user.UserService;

@Named
@SessionScoped
public class SessionBean extends AbstractBean {

	private static final long serialVersionUID = -8079836415042166193L;
	
	@Inject
	private UserService userService;

	@Produces
	private List<Action> actions = new ArrayList<Action>();

	@Produces
	private List<Bookmark> bookmarks = new ArrayList<Bookmark>();

	private Locale locale;

	private int rowsPerPage = 10;

	@Produces
	private List<SavedSearch> savedSearches = new ArrayList<SavedSearch>();

	private User user;

	public List<Action> getActions() {
		return actions;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public Locale getLocale() {
		return locale;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}
	
	public List<SavedSearch> getSavedSearches() {
		return savedSearches;
	}

	public User getUser() {
		return user;
	}

	@PostConstruct
	public void init() {
		user = userService.getByPrimaryKey(User.class, 901L);
		rowsPerPage = user.getRowsPerPage();
		locale = new Locale(user.getLanguage());
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public void setLocale(Locale locale){
		this.locale = locale;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setSavedSearches(List<SavedSearch> savedSearches) {
		this.savedSearches = savedSearches;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
