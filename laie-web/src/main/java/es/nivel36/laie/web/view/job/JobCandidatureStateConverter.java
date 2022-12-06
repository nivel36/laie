package es.nivel36.laie.web.view.job;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
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
