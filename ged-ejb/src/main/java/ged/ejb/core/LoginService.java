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
import ged.ejb.core.model.Repository;
import ged.ejb.user.User;
import ged.ejb.user.UserDao;

@Stateless
public class LoginService {

	@Inject
	@Repository
	private UserDao userDao;

	@Audited(action = ActionType.LOGIN)
	public User login(final String email, final String password) {
		final User user = this.userDao.findUserByEmail(email);
		try {
			final byte[] hashPassword = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));
			final char[] hashBase64Password = DatatypeConverter.printBase64Binary(hashPassword).toCharArray();
			if (!Arrays.equals(user.getPassword(), hashBase64Password)) {
				throw new LoginException();
			}
			user.setLastConnection(LocalDateTime.now());
			return this.userDao.save(user);
		}
		catch (NoSuchAlgorithmException | LoginException e) {
			throw new BadLoginException();
		}
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}

}