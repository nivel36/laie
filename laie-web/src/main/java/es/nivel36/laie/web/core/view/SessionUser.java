package es.nivel36.laie.web.core.view;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.core.bookmark.BookmarkService;
import es.nivel36.laie.ejb.user.BadManagerException;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.login.LoginService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class SessionUser implements Serializable {

	private static final long serialVersionUID = 6994840904536694158L;

	private static final Logger logger = LoggerFactory.getLogger(SessionUser.class);

	private Locale locale;

	private List<User> team;

	private User user;

	private List<Bookmark> bookmarks;

	private transient @Inject BookmarkService bookmarkService;

	private transient @Inject UserService userService;

	private transient @Inject LoginService loginService;

	public void load(final String username, final String role) {
		Objects.requireNonNull(username);
		logger.info("User {} has init his/her session", username);
		this.loadUserData(username);
		if (areUserRoleAndLoginRoleEquals(role)) {
			throw new SecurityException();
		}
		this.user.setLastConnection(LocalDateTime.now());
		try {
			this.user = this.userService.updateUser(user);
		} catch (DuplicateEmailException | BadManagerException e) {
			// Can't happen
		}
	}

	private boolean areUserRoleAndLoginRoleEquals(final String role) {
		return "laie.admin".equals(role) && !this.isAdmin() || !"laie.admin".equals(role) && this.isAdmin();
	}

	public boolean isActive() {
		return this.user != null;
	}

	public boolean isManagerOf(final User subordinate) {
		Objects.requireNonNull(subordinate);
		return this.team.contains(subordinate);
	}

	private void loadUserData(final String email) {
		this.user = this.userService.findUserByEmail(email);
		this.locale = new Locale(this.user.getLanguage());
		this.team = this.userService.findSubordinateUsers(user);
		this.bookmarks = new ArrayList<>(this.user.getBookmarks());
	}

	public void refresh() {
		logger.trace("Refreshing session for user {}", this.user);
		this.loadUserData(this.user.getEmail());
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
