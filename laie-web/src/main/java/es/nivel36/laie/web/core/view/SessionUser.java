package es.nivel36.laie.web.core.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.core.bookmark.BookmarkService;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.login.LoginService;

@Named
@SessionScoped
public class SessionUser implements Serializable {

	private static final long serialVersionUID = 6994840904536694158L;

	private static final Logger logger = LoggerFactory.getLogger(SessionUser.class);

	private Locale locale;

	private List<User> team;

	private User user;

	private List<Bookmark> bookmarks;

	@Inject
	private transient BookmarkService bookmarkService;

	@Inject
	private transient UserService userService;

	@Inject
	private transient LoginService loginService;

	public void load(final String username, final String role) {
		Objects.requireNonNull(username);
		logger.info("User {} has init his/her session", username);

		if ("laie.admin".equals(role)) {
			this.loadUserData(username, Role.ADMIN);
		} else {
			this.loadUserData(username, Role.USER);
		}
	}

	public boolean isActive() {
		return this.user != null;
	}

	public boolean isManagerOf(final User subordinate) {
		Objects.requireNonNull(subordinate);
		return this.team.contains(subordinate);
	}

	private void loadUserData(final String email, final Role role) {
		this.user = this.userService.findUserByEmail(email);
		this.user.setRole(role);
		this.locale = new Locale(this.user.getLanguage());
		this.team = this.userService.findSubordinateUsers(user);
		this.bookmarks = new ArrayList<>(this.user.getBookmarks());
	}

	public void refresh() {
		logger.trace("Refreshing session for user {}", this.user);
		this.loadUserData(this.user.getEmail(), this.user.getRole());
	}

	public void logout() {
		loginService.logout(this.user.getEmail());
		Faces.redirect("/");
	}

	public User get() {
		return this.user;
	}

	public User getUser() {
		return this.user;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public List<User> getTeam() {
		return this.team;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public boolean isAdmin() {
		if (!this.isActive()) {
			return false;
		}
		return this.user.getRole().equals(Role.ADMIN);
	}

	public void addBookmark(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.user = this.bookmarkService.addBookmark(bookmark, user);
		this.bookmarks.add(bookmark);
	}

	public void removeFromBookmarks(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.user = this.bookmarkService.deleteBookmark(bookmark, user);
		this.bookmarks.remove(bookmark);
	}

	public boolean hasBookamrk(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		return this.bookmarks.contains(bookmark);
	}

	@Override
	public String toString() {
		if (!this.isActive()) {
			return "";
		}
		return this.user.toString();
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	public void setLoginService(final LoginService loginService) {
		Objects.requireNonNull(loginService);
		this.loginService = loginService;
	}

	public void setBookmarkService(final BookmarkService bookmarkService) {
		Objects.requireNonNull(bookmarkService);
		this.bookmarkService = bookmarkService;
	}
}
