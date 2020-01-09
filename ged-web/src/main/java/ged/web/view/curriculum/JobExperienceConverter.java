package ged.web.view.curriculum;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.JobExperience;

@FacesConverter(forClass = JobExperience.class, managed = true)
public class JobExperienceConverter implements Converter<JobExperience> {

	@Inject
	private CurriculumService curriculumService;

	@Override
	public JobExperience getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		return this.curriculumService.findJobExperience(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JobExperience value) {
		if (value == null) {
			return null;
		}
		return String.valueOf(value.getId());
	}
}
