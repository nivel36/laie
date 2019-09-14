package ged.web.view.user;

import java.util.Objects;

import ged.ejb.core.AbstractService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractLazyDataModel;

public class UserLazyDataModel extends AbstractLazyDataModel<User> {

	private static final long serialVersionUID = 1L;
	
	private transient UserService userService;

	public UserLazyDataModel(final UserService userService) {
		Objects.requireNonNull(userService, "UserService can't be null");
		this.userService = userService;
	}

	@Override
	protected AbstractService<User> getService() {
		return userService;
	}
}