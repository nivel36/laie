package es.nivel36.laie.web.view.job;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureEventType;

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