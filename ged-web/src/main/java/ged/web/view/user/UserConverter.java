package ged.web.view.user;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.user.User;
import ged.ejb.user.UserService;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, value = "userConverter")
public class UserConverter extends AbstractConverter<User> {

	@Inject
	private UserService userService;

	@Override
	protected AbstractService<User> getService() {
		return this.userService;
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}