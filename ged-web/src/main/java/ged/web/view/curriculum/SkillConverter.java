package ged.web.view.curriculum;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Skill;

@FacesConverter(value = "skillConverter", managed = true)
public class SkillConverter implements Converter<Skill> {

	@Inject
	private CurriculumService curriculumService;

	@Override
	public Skill getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		Skill skill = this.curriculumService.findSkill(value);
		if (skill == null) {
			skill = new Skill(value);
		}
		return skill;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Skill value) {
		return value.getName();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}
}
