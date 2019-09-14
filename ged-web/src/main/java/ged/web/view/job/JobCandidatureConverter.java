package ged.web.view.job;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ged.ejb.core.AbstractService;
import ged.ejb.job.candidature.JobCandidature;
import ged.ejb.job.candidature.JobCandidatureService;
import ged.web.core.view.AbstractConverter;


@FacesConverter(managed = true, forClass = JobCandidature.class)
public class JobCandidatureConverter extends AbstractConverter<JobCandidature>{
	
	@Inject
	private JobCandidatureService jobCandidatureService;

	@Override
	protected AbstractService<JobCandidature> getService() {
		return jobCandidatureService;
	}

	public void setJobCandidatureService(JobCandidatureService jobCandidatureService) {
		this.jobCandidatureService = jobCandidatureService;
	}
}
