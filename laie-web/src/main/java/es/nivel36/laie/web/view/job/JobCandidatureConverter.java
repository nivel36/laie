package es.nivel36.laie.web.view.job;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;

@FacesConverter(managed = true, forClass = JobCandidature.class)
public class JobCandidatureConverter implements Converter<JobCandidature> {

	@Inject
	private JobCandidatureService jobCandidatureService;

	@Override
	public JobCandidature getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (value == null) {
			return null;
		}
		return this.jobCandidatureService.findContactByUid(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final JobCandidature value) {
		if (value == null) {
			return null;
		}
		return value.getUid();
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}
}
