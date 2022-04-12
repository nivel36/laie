package es.nivel36.laie.web.core.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.bookmark.Bookmark;
import es.nivel36.laie.ejb.user.BadManagerException;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.LoginService;

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
	private transient UserService userService;

	@Inject
	private LoginService loginService;

	public void load(final String username) {
		Objects.requireNonNull(username);
		logger.info("User {} has init his/her session", username);
		this.loadUserData(username);
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

	public void logout() throws ServletException, IOException {
		loginService.logout(this.user.getEmail());
		Faces.logout();
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

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	public void setLoginService(final LoginService loginService) {
		Objects.requireNonNull(loginService);
		this.loginService = loginService;
	}
	
	public void addBookmark(final Bookmark bookmark) {
		Objects.requireNonNull(bookmark);
		this.bookmarks.add(bookmark);
		this.user.getBookmarks().add(bookmark);
		try {
			this.user = userService.updateUser(user);
		} catch (DuplicateEmailException | BadManagerException e) {
			// It can't happen
		}
	}
	
	public void removeFromBookmarks(final Bookmark bookmark)  {
		Objects.requireNonNull(bookmark);
		this.bookmarks.remove(bookmark);
		this.user.getBookmarks().remove(bookmark);
		try {
			this.user = userService.updateUser(user);
		} catch (DuplicateEmailException | BadManagerException e) {
			// It can't happen
		}
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
}
