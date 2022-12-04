package es.nivel36.laie.web.view.job;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateService;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = JobCandidatureState.class)
public class JobCandidatureStateConverter implements Converter<JobCandidatureState> {
	
	private @Inject JobCandidatureStateService jobCandidatureStateService;
	
	@Override
	public JobCandidatureState getAsObject(FacesContext context, UIComponent component, String value) {
		return jobCandidatureStateService.findByName(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JobCandidatureState value) {
		return value.getName();
	}
}
