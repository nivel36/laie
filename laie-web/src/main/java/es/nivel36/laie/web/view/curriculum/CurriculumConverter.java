package es.nivel36.laie.web.view.curriculum;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.curriculum.CurriculumDto;
import es.nivel36.laie.ejb.curriculum.CurriculumService;

@FacesConverter(forClass = CurriculumDto.class, managed = true)
public class CurriculumConverter implements Converter<CurriculumDto> {

	@Inject
	private CurriculumService curriculumService;

	@Override
	public CurriculumDto getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.curriculumService.findCurriculumByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final CurriculumDto value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		Objects.requireNonNull(curriculumService);
		this.curriculumService = curriculumService;
	}
}
