package ged.web.view.candidate;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.core.tag.Tag;
import ged.ejb.core.tag.TagService;

@FacesConverter(managed = true, forClass = Tag.class)
public class TagConverterForClass implements Converter<Tag> {

	@Inject
	private TagService tagService;

	@Override
	public Tag getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		Tag tag = this.tagService.findByLabel(value);
		if (tag != null) {
			tag = new Tag(value);
		}
		return tag;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Tag value) {
		return value.getLabel();
	}
}