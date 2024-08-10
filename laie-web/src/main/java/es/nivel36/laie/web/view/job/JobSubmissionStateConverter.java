package es.nivel36.laie.web.view.job;

import es.nivel36.laie.ejb.job.submission.JobSubmissionState;
import es.nivel36.laie.ejb.job.submission.JobSubmissionStateService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = JobSubmissionState.class)
public class JobSubmissionStateConverter implements Converter<JobSubmissionState> {
	
	private @Inject JobSubmissionStateService jobSubmissionStateService;
	
	@Override
	public JobSubmissionState getAsObject(FacesContext context, UIComponent component, String value) {
		return jobSubmissionStateService.findByName(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, JobSubmissionState value) {
		return value.getName();
	}
}
