package ged.ejb.core;

import java.time.LocalDateTime;
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
	@Audited(action = ActionType.LOGIN)
	public User login(final String email) {
		final User user = this.userSerivce.findUserByEmail(email);
		user.setLastConnection(LocalDateTime.now());
		return this.userSerivce.update(user);
	}
}