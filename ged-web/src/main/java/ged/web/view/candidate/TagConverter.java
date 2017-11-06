package ged.web.view.candidate;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ged.ejb.core.tag.Tag;

@FacesConverter(value = "i18nConverter")
public class TagConverter implements Converter {

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		final Tag tag = new Tag();
		tag.setLabel(value);
		return tag;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null) {
			return null;
		}
		final Tag tag = (Tag) value;
		return tag.getLabel();
	}
}