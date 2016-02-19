package ged.web.view.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ged.ejb.core.user.Role;
import ged.web.core.view.ApplicationBean;

@FacesConverter(forClass = Role.class)
public class RoleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		List<Role> roles = getAppBean().getRoles();
		for (Role role : roles) {
			if (role.getName().equals(value)) {
				return role;
			}
		}
		throw new NoSuchElementException(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Role role = (Role) value;
		return role.getName();
	}

	protected ApplicationBean getAppBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{applicationBean}", ApplicationBean.class);
	}
}
