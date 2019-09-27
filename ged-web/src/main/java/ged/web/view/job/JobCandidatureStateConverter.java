package ged.web.view.job;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.job.candidature.JobCandidatureState;
import ged.ejb.job.candidature.JobCandidatureStateService;
import ged.web.core.view.AbstractConverter;

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
