package ged.web.view.candidate;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.core.Service;
import ged.ejb.core.tag.Tag;
import ged.ejb.core.tag.TagService;
import ged.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = Tag.class)
public class TagConverter extends AbstractConverter<Tag> {

	@Inject
	private TagService tagService;

	@Override
	protected Service<Tag> getService() {
		return this.tagService;
	}

	public void setTagService(final TagService tagService) {
		this.tagService = tagService;
	}
}