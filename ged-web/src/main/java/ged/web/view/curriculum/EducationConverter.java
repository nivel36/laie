package ged.web.view.curriculum;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Education;

@FacesConverter(forClass = Education.class, managed = true)
public class EducationConverter implements Converter<Education> {

	@Inject
	private CurriculumService curriculumService;

	@Override
	public Education getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.curriculumService.findEducation(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Education value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}
}
