package es.nivel36.laie.web.view.job;

import java.util.Objects;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.google.common.util.concurrent.AbstractService;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateDto;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateService;
import es.nivel36.laie.ejb.job.offer.JobOfferDto;
import es.nivel36.laie.ejb.job.offer.JobOfferService;

@FacesConverter(managed = true, forClass = JobCandidatureState.class)
public class JobCandidatureStateConverter implements Converter<JobCandidatureStateDto> {

	@Inject
	private JobCandidatureStateService jobCandidatureStateService;

	@Override
	public JobCandidatureStateDto getAsObject(final FacesContext context, final UIComponent component,
			final String value) {
		if (value == null) {
			return null;
		}
		return null;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component,
			final JobCandidatureStateDto value) {
		if (value == null) {
			return null;
		}
		return null;
	}

	public void setJobCandidatureStateService(final JobCandidatureStateService jobCandidatureStateService) {
		Objects.requireNonNull(jobCandidatureStateService);
		this.jobCandidatureStateService = jobCandidatureStateService;
	}
}
