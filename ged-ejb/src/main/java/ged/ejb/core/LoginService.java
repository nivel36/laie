package ged.ejb.core;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.Repository;
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
		Objects.requireNonNull(email);
		Objects.requireNonNull(password);
		final User user = this.userDao.findUserByEmail(email);
		final Credential credential = this.userDao.findUserCredential(user);
		if (!credential.isValid(password)) {
			throw new LoginException("Passwords doesn't match");
		}
		user.setLastConnection(LocalDateTime.now());
		return this.userDao.save(user);
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}
}