package es.nivel36.laie.web.view.job;

import java.util.Objects;

import javax.faces.convert.FacesConverter;

import es.nivel36.laie.ejb.job.candidature.JobCandidature;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = JobCandidature.class)
public class JobCandidatureConverter extends AbstractConverter<JobCandidature> {

	private @Inject JobCandidatureService jobCandidatureService;

	@Override
	protected JobCandidature getAsObject(final Long id) {
		if (id == null) {
			return null;
		}
		return jobCandidatureService.findJobCandidature(id);
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}

}
