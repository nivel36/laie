package es.nivel36.laie.web.view.job;

import es.nivel36.laie.ejb.job.submission.JobSubmissionEventType;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = JobSubmissionEventType.class)
public class JobSubmissionEventTypeConverter implements Converter<JobSubmissionEventType> {

	@Override
	public JobSubmissionEventType getAsObject(FacesContext context, UIComponent component, String value) {
		return JobSubmissionEventType.valueOf(value.toUpperCase());
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JobSubmissionEventType value) {
		return value.getName();
	}
}