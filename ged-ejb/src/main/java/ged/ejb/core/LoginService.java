package ged.ejb.core;

import java.time.LocalDateTime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Stateless
public class LoginService {

	@Inject
	private UserService userSerivce;

	@Audited(action = ActionType.LOGIN)
	public User login(final String email) {
		final User user = this.userSerivce.findUserByEmail(email);
		user.setLastConnection(LocalDateTime.now());
		return this.userSerivce.save(user);
	}

	public void setUserSerivce(final UserService userSerivce) {
		this.userSerivce = userSerivce;
	}
}