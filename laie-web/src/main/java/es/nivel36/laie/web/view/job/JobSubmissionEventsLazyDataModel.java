package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.submission.JobSubmissionEvent;
import es.nivel36.laie.ejb.job.submission.JobSubmissionService;
import jakarta.inject.Inject;

public class JobSubmissionEventsLazyDataModel extends LazyDataModel<JobSubmissionEvent> {

	private static final long serialVersionUID = -833385360783160691L;

	private JobOffer jobOffer;

	private @Inject JobSubmissionService jobSubmissionService;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		if (jobOffer == null) {
			throw new IllegalStateException();
		}
		return (int) jobSubmissionService.countJobSubmissionEvents(jobOffer);
	}

	@Override
	public List<JobSubmissionEvent> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		if (jobOffer == null) {
			throw new IllegalStateException();
		}
		return jobSubmissionService.findJobSubmissionEvents(jobOffer, Page.of(first, pageSize));
	}

	public void setJobOffer(JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		this.jobOffer = jobOffer;
	}

	public void setJobSubmissionService(JobSubmissionService jobSubmissionService) {
		Objects.requireNonNull(jobSubmissionService);
		this.jobSubmissionService = jobSubmissionService;
	}

}
