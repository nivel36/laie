package ged.web.view.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.user.role.Role;
import ged.ejb.user.role.RoleService;

@FacesConverter(managed = true, forClass = Role.class)
public class RoleConverter implements Converter<Role> {

	@Inject
	private RoleService roleService;

	@Override
	public Role getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		try {
			final long roleId = Long.parseLong(value);
			return this.roleService.find(roleId);
		}
		catch (final NumberFormatException e) {
			throw new ConverterException(value + " is not a valid id");
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Role value) {
		if (value == null) {
			return null;
		}
		return String.valueOf(value.getId());
	}
}