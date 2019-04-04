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
	public User login(final String email, final String password) throws LoginException {
		final User user = this.userDao.findUserByEmail(email);
		final byte[] salt = user.getCredential().getSalt();
		try {
			final byte[] hashPassword = digestPassword(password, salt);
			final char[] hashBase64Password = DatatypeConverter.printBase64Binary(hashPassword).toCharArray();
			System.out.println(hashBase64Password.toString());
			if (passwordMatch(user.getCredential().getPassword(), hashBase64Password)) {
				throw new LoginException("Passwords doesn't match");
			}
			user.setLastConnection(LocalDateTime.now());
			return this.userDao.save(user);
		} catch (NoSuchAlgorithmException  e) {
			 throw new SecurityException(e);
		}
	}

	private boolean passwordMatch(final char[] storedPassword, final char[] hashBase64Password) {
		return !Arrays.equals(storedPassword, hashBase64Password);
	}

	private byte[] digestPassword(final String password, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(salt);
		return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}
}