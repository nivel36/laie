package es.nivel36.laie.core.view;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Objects;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.user.UserDto;
import es.nivel36.laie.user.UserService;

@Named
@SessionScoped
public class SessionUser implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private static final long serialVersionUID = 1L;

	private Locale locale;

	private UserDto user;

	@Inject
	private transient UserService userService;

	public UserDto get() {
		return this.user;
	}

	public UserDto getUser() {
		return this.user;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public void load(String username) {
		Objects.requireNonNull(username);
		logger.info("User {} has init his/her session", username);
		this.loadUserData(username);
	}

	public boolean isActive() {
		return this.user != null;
	}

	private void loadUserData(final String email) {
		this.user = this.userService.findUserByEmail(email);
		this.locale = this.user.getLocale();
	}

	public void refresh() {
		logger.trace("Refreshing maxAge for user {}", this.user.getEmail());
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
		return this.user.toString();
	}
}
