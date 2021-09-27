package es.nivel36.laie.web.view.user;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

@FacesConverter(managed = true, value = "userConverter")
public class UserConverter implements Converter<User> {

	@Inject
	private UserService userService;

	@Override
	public User getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.userService.findByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final User value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setUserService(final UserService userService) {
		Objects.requireNonNull(userService);
		this.userService = userService;
	}
}