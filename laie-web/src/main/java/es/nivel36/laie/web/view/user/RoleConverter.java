package es.nivel36.laie.web.view.user;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.user.Role;

@FacesConverter(managed = true, forClass = Role.class)
public class RoleConverter implements Converter<Role> {

	@Override
	public Role getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return Role.valueOf(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Role value) {
		if (value == null) {
			return null;
		}
		return value.name();
	}
}