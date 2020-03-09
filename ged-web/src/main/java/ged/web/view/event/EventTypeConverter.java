package ged.web.view.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import ged.ejb.event.JobCandidatureEventType;

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
