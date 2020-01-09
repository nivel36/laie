package ged.web.view.curriculum;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.curriculum.Curriculum;
import ged.ejb.curriculum.CurriculumService;

@FacesConverter(forClass = Curriculum.class, managed = true)
public class CurriculumConverter implements Converter<Curriculum> {

	@Inject
	private CurriculumService curriculumService;

	@Override
	public Curriculum getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		return curriculumService.findByUid(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Curriculum value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setCurriculumService(CurriculumService curriculumService) {
		Objects.requireNonNull(curriculumService);
		this.curriculumService = curriculumService;
	}
}
