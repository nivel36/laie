package es.nivel36.laie.user;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(managed = true, value = "userConverter")
public class UserConverter implements Converter<UserDto> {

	@Inject
	private UserService userService;

	@Override
	public UserDto getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.userService.findUserByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final UserDto value) {
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
