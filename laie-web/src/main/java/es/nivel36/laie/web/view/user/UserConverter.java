package es.nivel36.laie.web.view.user;

import java.util.Objects;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.AbstractConverter;

@FacesConverter(managed = true, forClass = User.class)
public class UserConverter extends AbstractConverter<User> {

	@Inject
	private UserService userService;

	@Override
	protected User getAsObject(Long id) {
		return userService.findUserById(id);
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}