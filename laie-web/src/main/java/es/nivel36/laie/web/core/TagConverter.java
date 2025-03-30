package es.nivel36.laie.web.core;

import es.nivel36.laie.ejb.core.tag.Tag;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(managed = true, forClass = Tag.class)
public class TagConverter implements Converter<Tag> {

	@Override
	public Tag getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) {
			return null;
		}
		return new Tag(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Tag value) {
		if (value == null) {
			return null;
		}
		return value.getLabel();
	}
}
