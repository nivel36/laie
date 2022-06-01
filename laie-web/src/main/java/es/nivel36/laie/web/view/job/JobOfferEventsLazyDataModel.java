package es.nivel36.laie.web.view.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.ejb.job.offer.JobOfferStateEvent;

public class JobOfferEventsLazyDataModel extends LazyDataModel<JobOfferStateEvent> {

	private static final long serialVersionUID = -833385360783160691L;

	private JobOffer jobOffer;

	@Inject
	private JobOfferService jobOfferService;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		if (jobOffer == null) {
			throw new IllegalStateException();
		}
		return (int) jobOfferService.countJobOfferEventsByJobOffer(jobOffer);
	}

	@Override
	public List<JobOfferStateEvent> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		if (jobOffer == null) {
			throw new IllegalStateException();
		}
		return jobOfferService.findJobOfferEventsByJobOffer(jobOffer, Page.of(first, pageSize));
	}

	public void setJobOffer(JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		this.jobOffer = jobOffer;
	}

	public void setJobOfferService(JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService);
		this.jobOfferService = jobOfferService;
	}

}
