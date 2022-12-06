package es.nivel36.laie.web.view.job;

import java.util.Objects;

import es.nivel36.laie.ejb.job.candidature.JobCandidatureEvent;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = JobCandidatureEvent.class)
public class JobCandidatureEventConverter extends AbstractConverter<JobCandidatureEvent> {

	private @Inject JobCandidatureService jobCandidatureService;

	@Override
	protected JobCandidatureEvent getAsObject(final Long id) {
		if (id == null) {
			return null;
		}
		return jobCandidatureService.findJobCandidatureEvent(id);
	}

	public void setJobCandidatureService(final JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}
}
