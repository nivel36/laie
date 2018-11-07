package ged.web.core;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("toStringConverter")
public class ToStringConverter implements Converter<Object> {

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return value;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		return value.toString();
	}

}
