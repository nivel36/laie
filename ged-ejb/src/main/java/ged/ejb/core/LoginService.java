package ged.ejb.core;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.Repository;
import ged.ejb.core.security.CriptoUtil;
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
			final byte[] salt = user.getCredential().getSalt();
			final char[] hashedBase64Password = CriptoUtil.hashBase64Password(password, salt);
			if (CriptoUtil.passwordMatch(user.getCredential().getPassword(), hashedBase64Password)) {
				throw new LoginException("Passwords doesn't match");
			}
			user.setLastConnection(LocalDateTime.now());
			return this.userDao.save(user);
		} catch (NoSuchAlgorithmException  e) {
			 throw new SecurityException(e);
		}
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}
}