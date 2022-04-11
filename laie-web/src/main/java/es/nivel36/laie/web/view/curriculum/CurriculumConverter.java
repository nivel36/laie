package es.nivel36.laie.web.view.curriculum;

import java.util.Objects;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.curriculum.Curriculum;
import es.nivel36.laie.ejb.curriculum.CurriculumService;
import es.nivel36.laie.web.core.AbstractConverter;

@FacesConverter(forClass = Curriculum.class, managed = true)
public class CurriculumConverter extends AbstractConverter<Curriculum> {

	@Inject
	private CurriculumService curriculumService;

	public void setCurriculumService(final CurriculumService curriculumService) {
		Objects.requireNonNull(curriculumService);
		this.curriculumService = curriculumService;
	}

	@Override
	protected Curriculum getAsObject(Long id) {
		return curriculumService.findCurriculumById(id);
	}
}
