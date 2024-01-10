package es.nivel36.laie.web.view.user;

import java.util.Objects;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter("userConverter")
public class UserConverterForName extends AbstractConverter<User> {

	private @Inject UserService userService;

	@Override
	protected User getAsObject(Long id) {
		return userService.findUserById(id);
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}