package es.nivel36.laie.web.view.event;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureEventType;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = JobCandidatureEventType.class)
public class EventTypeConverter implements Converter<JobCandidatureEventType> {

	@Override
	public JobCandidatureEventType getAsObject(final FacesContext context, final UIComponent component,
			final String value) {
		if (value == null) {
			return null;
		}
		for (final JobCandidatureEventType jobCandidatureEventType : JobCandidatureEventType.values()) {
			if (jobCandidatureEventType.getName().equals(value)) {
				return jobCandidatureEventType;
			}
		}
		throw new ConverterException("No event type wiht name " + value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component,
			final JobCandidatureEventType value) {
		if (value == null) {
			return null;
		}
		return value.getName();
	}
}
