package es.nivel36.laie.web.view.job;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureEventType;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = JobCandidatureEventType.class)
public class JobCandidatureEventTypeConverter implements Converter<JobCandidatureEventType> {

	@Override
	public JobCandidatureEventType getAsObject(FacesContext context, UIComponent component, String value) {
		return JobCandidatureEventType.valueOf(value.toUpperCase());
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JobCandidatureEventType value) {
		return value.getName();
	}
}