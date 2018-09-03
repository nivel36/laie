package ged.web.core.view;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
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

	private static final long serialVersionUID = -8079836415042166193L;
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private Locale locale;

	private List<User> team;

	private User user;

	@Inject
	private transient ExternalContext externalContext;

	public void setExternalContext(ExternalContext externalContext) {
		this.externalContext = externalContext;
	}

	@Inject
	private transient UserService userService;

	public Locale getLocale() {
		return this.locale;
	}

	public int getRowsPerPage() {
		return this.user.getRowsPerPage();
	}

	public List<User> getTeam() {
		return this.team;
	}

	public User get() {
		return this.user;
	}

	public boolean isAdmin() {
		return user.isAdmin();
	}

	@PostConstruct
	public void init() {
		final String email = externalContext.getRemoteUser();
		logger.info("User {} has init his/her session", email);
		loadUserData(email);
	}

	private void loadUserData(final String email) {
		this.user = userService.findUserByEmail(email);
		this.locale = new Locale(this.user.getLanguage());
		this.team = userService.findSubordinateUsers(this.user);
	}

	public boolean hasPermissionToEdit(final Ownerable entity) {
		Objects.requireNonNull(entity);
		if (isAdmin()) {
			return true;
		}
		final User owner = entity.getOwner();
		Objects.requireNonNull(owner);
		return isOwnerOrHisManager(owner);
	}

	private boolean isOwnerOrHisManager(final User owner) {
		if (user.equals(owner)) {
			return true;
		}
		return isManagerOf(owner);
	}

	public boolean isManagerOf(final User subordinate) {
		Objects.requireNonNull(subordinate);
		return getTeam().contains(subordinate);
	}

	public void refresh() {
		logger.trace("Refreshing session for user {}", user.getEmail());
		loadUserData(user.getEmail());
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}
