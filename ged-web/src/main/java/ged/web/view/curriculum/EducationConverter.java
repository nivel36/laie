package ged.web.view.curriculum;

import java.lang.invoke.MethodHandles;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.curriculum.CurriculumService;
import ged.ejb.curriculum.Education;

@FacesConverter(forClass = Education.class, managed = true)
public class EducationConverter implements Converter<Education> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private CurriculumService curriculumService;

	@Override
	public Education getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		try {
			final long longValue = Long.valueOf(value);
			return this.curriculumService.findEducation(longValue);
		} catch (final NumberFormatException e) {
			logger.error("Conversion error (Long to String): {}", value);
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Education value) {
		if (value == null) {
			return null;
		}
		final long id = value.getId();
		return String.valueOf(id);
	}

	public void setCurriculumService(final CurriculumService curriculumService) {
		this.curriculumService = curriculumService;
	}
}
