package es.nivel36.laie.web.view.user;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.user.SimpleUserDto;
import es.nivel36.laie.ejb.user.UserDto;
import es.nivel36.laie.ejb.user.UserService;

@FacesConverter(managed = true, value = "simpleUserConverter")
public class SimpleUserConverter implements Converter<SimpleUserDto> {

	@Inject
	private UserService userService;

	@Override
	public SimpleUserDto getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		final UserDto user = this.userService.findUserByUid(value);
		if (user == null) {
			return null;
		}
		return new SimpleUserDto(user);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final SimpleUserDto value) {
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
