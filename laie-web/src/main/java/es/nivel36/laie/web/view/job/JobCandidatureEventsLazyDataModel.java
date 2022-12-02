package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureEvent;
import es.nivel36.laie.ejb.job.candidature.JobCandidatureService;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import jakarta.inject.Inject;

public class JobCandidatureEventsLazyDataModel extends LazyDataModel<JobCandidatureEvent> {

	private static final long serialVersionUID = -833385360783160691L;

	private JobOffer jobOffer;

	private @Inject JobCandidatureService jobCandidatureService;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		if (jobOffer == null) {
			throw new IllegalStateException();
		}
		return (int) jobCandidatureService.countJobCandidatureEvents(jobOffer);
	}

	@Override
	public List<JobCandidatureEvent> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		if (jobOffer == null) {
			throw new IllegalStateException();
		}
		return jobCandidatureService.findJobCandidatureEvents(jobOffer, Page.of(first, pageSize));
	}

	public void setJobOffer(JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		this.jobOffer = jobOffer;
	}

	public void setJobCandidatureService(JobCandidatureService jobCandidatureService) {
		Objects.requireNonNull(jobCandidatureService);
		this.jobCandidatureService = jobCandidatureService;
	}

}
