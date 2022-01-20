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

import es.nivel36.laie.ejb.core.bookmark.BookmarkDto;
import es.nivel36.laie.ejb.core.bookmark.BookmarkService;
import es.nivel36.laie.ejb.user.Role;
import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.LoginService;

@Named
@SessionScoped
public class SessionUser implements Serializable {

	private static final long serialVersionUID = 6994840904536694158L;

	private static final Logger logger = LoggerFactory.getLogger(SessionUser.class);

	private Locale locale;

	private List<SimpleUserDto> team;

	private UserDto user;

	private List<BookmarkDto> bookmarks;

	@Inject
	private transient UserService userService;

	@Inject
	private transient BookmarkService bookmarkService;

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

	public boolean isManagerOf(final String subordinateUid) {
		Objects.requireNonNull(subordinateUid);
		for (final SimpleUserDto user : this.team) {
			if (user.getUid().equals(subordinateUid)) {
				return true;
			}
		}
		return false;
	}

	private void loadUserData(final String email) {
		this.user = this.userService.findUserByEmail(email);
		this.locale = new Locale(this.user.getLanguage());
		final String userUid = user.getUid();
		final List<UserDto> usersTeam = this.userService.findSubordinateUsers(userUid);
		this.team = new ArrayList<>(usersTeam.size());
		for (final UserDto user : usersTeam) {
			this.team.add(new SimpleUserDto(user));
		}
		this.bookmarks = this.bookmarkService.findBookmarksByUserUid(userUid);
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

	public UserDto get() {
		return this.user;
	}

	public UserDto getUser() {
		return this.user;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public List<SimpleUserDto> getTeam() {
		return this.team;
	}

	public List<BookmarkDto> getBookmarks() {
		return bookmarks;
	}

	public boolean isAdmin() {
		if (!this.isActive()) {
			return false;
		}
		return this.user.getRoleName().equals(Role.ADMIN.name());
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	public void setBookmarkService(final BookmarkService bookmarkService) {
		Objects.requireNonNull(bookmarkService);
		this.bookmarkService = bookmarkService;
	}

	public void setLoginService(final LoginService loginService) {
		Objects.requireNonNull(loginService);
		this.loginService = loginService;
	}

	@Override
	public String toString() {
		if (!this.isActive()) {
			return "";
		}
		return this.user.toString();
	}
}
