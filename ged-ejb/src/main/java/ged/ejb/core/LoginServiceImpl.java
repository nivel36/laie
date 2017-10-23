package ged.ejb.core;

import java.util.Calendar;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.action.Action.ActionType;
import ged.ejb.user.User;
import ged.ejb.user.UserService;

@Stateless
public class LoginServiceImpl implements LoginService {

	private final UserService userSerivce;

	@Inject
	public LoginServiceImpl(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userSerivce = userService;
	}
	
	@Override
	public User login(String email) {
		User user = userSerivce.findUserByEmail(email);
		return login(user);
	}
	
	@Override
	@Audited(action = ActionType.LOGIN)
	public User login(User user) {
		user.setLastConnection(Calendar.getInstance().getTime());
		return user;
	}
}