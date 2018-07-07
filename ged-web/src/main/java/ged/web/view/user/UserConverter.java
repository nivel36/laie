package ged.web.view.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.user.User;
import ged.ejb.user.UserService;

@FacesConverter(managed = true, forClass = User.class)
public class UserConverter implements Converter<User> {

	@Inject
	private UserService userService;

	@Override
	public User getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		try {
			final long userId = Long.parseLong(value);
			return this.userService.find(userId);
		}
		catch (final NumberFormatException e) {
			throw new ConverterException(value + " is not a valid id");
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final User value) {
		if (value == null) {
			return null;
		}
		return String.valueOf(value.getId());
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}