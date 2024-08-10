package es.nivel36.laie.web.view.event;

import es.nivel36.laie.ejb.job.submission.JobSubmissionEventType;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = JobSubmissionEventType.class)
public class EventTypeConverter implements Converter<JobSubmissionEventType> {

	@Override
	public JobSubmissionEventType getAsObject(final FacesContext context, final UIComponent component,
			final String value) {
		if (value == null) {
			return null;
		}
		for (final JobSubmissionEventType jobSubmissionEventType : JobSubmissionEventType.values()) {
			if (jobSubmissionEventType.getName().equals(value)) {
				return jobSubmissionEventType;
			}
		}
		throw new ConverterException("No event type wiht name " + value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component,
			final JobSubmissionEventType value) {
		if (value == null) {
			return null;
		}
		return value.getName();
	}
}
