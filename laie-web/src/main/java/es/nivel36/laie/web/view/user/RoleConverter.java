package es.nivel36.laie.web.view.user;

import es.nivel36.laie.ejb.user.Role;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

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