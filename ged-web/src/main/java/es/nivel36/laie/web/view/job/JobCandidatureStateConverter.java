package es.nivel36.laie.web.view.job;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.nivel36.laie.ejb.core.AbstractService;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureState;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureStateService;
import es.nivel36.laie.web.core.view.AbstractConverter;

@FacesConverter(managed = true, forClass = JobCandidatureState.class)
public class JobCandidatureStateConverter extends AbstractConverter<JobCandidatureState> {

	@Inject
	private JobCandidatureStateService jobCandidatureStateService;

	@Override
	protected AbstractService<JobCandidatureState> getService() {
		return this.jobCandidatureStateService;
	}

	public void setJobCandidatureStateService(final JobCandidatureStateService jobCandidatureStateService) {
		this.jobCandidatureStateService = jobCandidatureStateService;
	}
}
