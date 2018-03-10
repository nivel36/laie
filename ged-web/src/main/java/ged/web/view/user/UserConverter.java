package ged.web.view.user;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.user.User;
import ged.ejb.user.UserService;

@FacesConverter(forClass = User.class)
public class UserConverter implements Converter<User> {

	@Inject
	private UserService userService;

	@Override
	public User getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if ((value == null) || value.trim().equals("")) {
			return null;
		}
		final List<User> users = this.userService.search(value);
		for (final User user : users) {
			if (user.getFullName().equals(value)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final User value) {
		return value.getFullName();
	}
}