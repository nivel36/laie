package ged.web.core.view;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.model.Ownerable;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Named
@SessionScoped
public class SessionUser implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private Locale locale;

	private List<User> team;

	private User user;

	@Inject
	private transient UserService userService;

	public User get() {
		return this.user;
	}

	public User getUser() {
		return this.user;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public int getRowsPerPage() {
		return this.user.getRowsPerPage();
	}

	public List<User> getTeam() {
		return this.team;
	}

	public boolean hasPermissionToEdit(final Ownerable entity) {
		Objects.requireNonNull(entity);
		if (this.isAdmin()) {
			return true;
		}
		final User owner = entity.getOwner();
		Objects.requireNonNull(owner);
		return this.isOwnerOrHisManager(owner);
	}

	public void load(String username) {
		Objects.requireNonNull(username);
		logger.info("User {} has init his/her session", username);
		this.loadUserData(username);
	}

	public boolean isActive() {
		return this.user != null;
	}

	public boolean isAdmin() {
		if (!this.isActive()) {
			return false;
		}
		return this.user.isAdmin();
	}

	public boolean isManagerOf(final User subordinate) {
		Objects.requireNonNull(subordinate);
		return this.getTeam().contains(subordinate);
	}

	private boolean isOwnerOrHisManager(final User owner) {
		if (this.user.equals(owner)) {
			return true;
		}
		return this.isManagerOf(owner);
	}

	private void loadUserData(final String email) {
		this.user = this.userService.findByEmail(email);
		this.locale = new Locale(this.user.getLanguage());
		this.team = this.userService.findSubordinateUsers(email);
	}

	public void refresh() {
		logger.trace("Refreshing session for user {}", this.user.getEmail());
		this.loadUserData(this.user.getEmail());
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}

	@Override
	public String toString() {
		if (!this.isActive()) {
			return "";
		}
		return this.user.getFullName();
	}
}
