package ged.ejb.core;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.Repository;
import ged.ejb.core.security.CriptoUtil;
import ged.ejb.user.Credential;
import ged.ejb.user.User;
import ged.ejb.user.UserDao;

@Stateless
public class LoginService {

	@Inject
	@Repository
	private UserDao userDao;

	@Audited(action = ActionType.LOGIN)
	public User login(final String email, final String password) throws LoginException {
		try {
			final User user = this.userDao.findUserByEmail(email);
			final Credential credential = this.userDao.findUserCredential(user);
			final byte[] salt = credential.getSalt();
			final byte[] hashedPassword = CriptoUtil.digestPassword(password, salt);
			if (CriptoUtil.passwordMatch(credential.getPassword(), hashedPassword)) {
				throw new LoginException("Passwords doesn't match");
			}
			user.setLastConnection(LocalDateTime.now());
			return this.userDao.save(user);
		} catch (NoSuchAlgorithmException  e) {
			 throw new SecurityException(e);
		}
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}
}