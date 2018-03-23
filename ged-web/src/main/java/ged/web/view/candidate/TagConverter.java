package ged.web.view.candidate;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.candidate.CandidateService;
import ged.ejb.core.tag.Tag;

@FacesConverter(forClass = Tag.class)
public class TagConverter implements Converter<Tag> {

	@Inject
	private CandidateService candidateService;

	@Override
	public Tag getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		final List<Tag> tags = this.candidateService.findTags();
		for (final Tag tag : tags) {
			if (tag.getLabel().equals(value)) {
				return tag;
			}
		}
		final Tag tag = new Tag();
		tag.setLabel(value);
		return tag;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Tag value) {
		if (value == null) {
			return null;
		}
		return value.getLabel();
	}
}