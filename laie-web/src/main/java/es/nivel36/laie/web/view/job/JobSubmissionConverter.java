package es.nivel36.laie.web.view.job;

import java.util.Objects;

import es.nivel36.laie.ejb.job.submission.JobSubmission;
import es.nivel36.laie.ejb.job.submission.JobSubmissionService;
import es.nivel36.laie.web.core.AbstractConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(managed = true, forClass = JobSubmission.class)
public class JobSubmissionConverter extends AbstractConverter<JobSubmission> {

	private @Inject JobSubmissionService jobSubmissionService;

	@Override
	protected JobSubmission getAsObject(final Long id) {
		if (id == null) {
			return null;
		}
		return jobSubmissionService.findJobSubmissionById(id);
	}

	public void setJobSubmissionService(final JobSubmissionService jobSubmissionService) {
		Objects.requireNonNull(jobSubmissionService);
		this.jobSubmissionService = jobSubmissionService;
	}

}
