package ged.ejb.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import static java.nio.charset.StandardCharsets.UTF_8;

@Stateless
public class LoginService {

	@Inject
	private UserService userService;

	@Audited(action = ActionType.LOGIN)
	public User saveLastConnection(final String email) {
		final User user = this.userService.findUserByEmail(email);
		user.setLastConnection(LocalDateTime.now());
		return this.userService.save(user);
	}

	@Audited(action = ActionType.LOGIN)
	public User login(final String email, final String password) {
		final User user = this.userService.findUserByEmail(email);
		try {
			byte[] hashPassword = MessageDigest.getInstance("SHA-256").digest(password.getBytes(UTF_8));
			if (!user.getPassword().equals(hashPassword)) {
				throw new LoginException();
			}
			user.setLastConnection(LocalDateTime.now());
			return this.userService.save(user);
		} catch (NoSuchAlgorithmException | LoginException e) {
			throw new BadLoginException();
		}
	}

	public void setUserSerivce(final UserService userSerivce) {
		this.userService = userSerivce;
	}
}