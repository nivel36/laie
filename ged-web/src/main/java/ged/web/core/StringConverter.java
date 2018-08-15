package ged.web.core;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Workaround for https://github.com/javaserverfaces/mojarra/issues/4367
 *
 * @author Abel
 */
@FacesConverter("stringConverter")
public class StringConverter implements Converter<String> {

	
	@Override
	public String getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return value;
	}

	
	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final String value) {
		return value;
	}
}