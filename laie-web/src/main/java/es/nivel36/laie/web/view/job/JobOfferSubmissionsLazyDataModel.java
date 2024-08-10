package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.submission.JobSubmission;
import es.nivel36.laie.ejb.job.submission.JobSubmissionService;
import jakarta.inject.Inject;

public class JobOfferSubmissionsLazyDataModel extends LazyDataModel<JobSubmission> {
	
	private static final long serialVersionUID = -4040506548394973503L;

	private transient @Inject JobSubmissionService service;
	
	private JobOffer jobOffer;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		Objects.requireNonNull(jobOffer);
		return (int) service.countJobOffersJobCanditures(jobOffer);
	}

	@Override
	public List<JobSubmission> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		Objects.requireNonNull(jobOffer);
		return service.findJobSubmissionsByJobOffer(jobOffer, Page.of(first, pageSize));
	}

	public void setService(JobSubmissionService service) {
		Objects.requireNonNull(service);
		this.service = service;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
