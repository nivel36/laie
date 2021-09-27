package es.nivel36.laie.web.view.job;

import java.util.Objects;

import es.nivel36.laie.ejb.core.AbstractIndexedService;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.job.offer.JobOfferService;
import es.nivel36.laie.web.core.view.AbstractLazyDataModel;

public class JobOfferLazyDataModel extends AbstractLazyDataModel<JobOffer> {

	private static final long serialVersionUID = 1L;

	private transient JobOfferService jobOfferService;

	public JobOfferLazyDataModel(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService, "JobOfferService can't be null");
		this.jobOfferService = jobOfferService;
	}

	@Override
	protected AbstractIndexedService<JobOffer> getService() {
		return this.jobOfferService;
	}
}