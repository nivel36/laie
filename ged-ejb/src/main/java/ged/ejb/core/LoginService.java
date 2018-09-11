package ged.ejb.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.xml.bind.DatatypeConverter;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Stateless
public class LoginService {

	@Inject
	private UserService userService;

	@Audited(action = ActionType.LOGIN)
	public User login(final String email, final String password) {
		final User user = this.userService.findUserByEmail(email);
		try {
			final byte[] hashPassword = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));

			final char[] hashBase64Password = DatatypeConverter.printBase64Binary(hashPassword).toCharArray();

			if (!Arrays.equals(user.getPassword(), hashBase64Password)) {
				throw new LoginException();
			}
			user.setLastConnection(LocalDateTime.now());
			return this.userService.save(user);
		}
		catch (NoSuchAlgorithmException | LoginException e) {
			throw new BadLoginException();
		}
	}

	@Audited(action = ActionType.LOGIN)
	public User saveLastConnection(final String email) {
		final User user = this.userService.findUserByEmail(email);
		user.setLastConnection(LocalDateTime.now());
		return this.userService.save(user);
	}

	public void setUserSerivce(final UserService userSerivce) {
		this.userService = userSerivce;
	}
}