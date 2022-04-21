package es.nivel36.laie.web.core;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.core.tag.Tag;

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
