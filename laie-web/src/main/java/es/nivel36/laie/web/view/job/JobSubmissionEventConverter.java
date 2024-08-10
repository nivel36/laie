package es.nivel36.laie.web.view.job;

import java.util.Objects;

import es.nivel36.laie.ejb.job.submission.JobSubmissionEvent;
import es.nivel36.laie.ejb.job.submission.JobSubmissionService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = JobSubmissionEvent.class)
public class JobSubmissionEventConverter extends AbstractConverter<JobSubmissionEvent> {

	private @Inject JobSubmissionService jobSubmissionService;

	@Override
	protected JobSubmissionEvent getAsObject(final Long id) {
		if (id == null) {
			return null;
		}
		return jobSubmissionService.findJobSubmissionEvent(id);
	}

	public void setJobSubmissionService(final JobSubmissionService jobSubmissionService) {
		Objects.requireNonNull(jobSubmissionService);
		this.jobSubmissionService = jobSubmissionService;
	}
}
