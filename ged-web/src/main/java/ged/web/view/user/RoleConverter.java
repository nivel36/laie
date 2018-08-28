package ged.web.view.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.user.UserService;
import ged.ejb.user.role.Role;

@FacesConverter(managed = true, forClass = Role.class)
public class RoleConverter implements Converter<Role> {

	@Inject
	private UserService userService;

	@Override
	public Role getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.userService.findRoleByName(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Role value) {
		if (value == null) {
			return null;
		}
		return value.getName();
	}

	public void setUserService(final UserService userService) {
		this.userService = userService;
	}
}