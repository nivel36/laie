package ged.web.view.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ged.ejb.user.role.Role;
import ged.web.core.view.ApplicationBean;

@FacesConverter(forClass = Role.class)
public class RoleConverter implements Converter<Role> {

	protected ApplicationBean getAppBean() {
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{applicationBean}", ApplicationBean.class);
	}

	@Override
	public Role getAsObject(final FacesContext context, final UIComponent component, final String value) {
		final List<Role> roles = this.getAppBean().getRoles();
		for (final Role role : roles) {
			if (role.getName().equals(value)) {
				return role;
			}
		}
		throw new NoSuchElementException(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Role value) {
		return value.getName();
	}
}