package ged.web.view.job;

import java.util.Objects;

import ged.ejb.core.AbstractService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.web.core.view.AbstractLazyDataModel;

public class JobOfferLazyDataModel extends AbstractLazyDataModel<JobOffer> {

	private static final long serialVersionUID = 6799896178978982561L;
	
	private transient JobOfferService jobOfferService;

	public JobOfferLazyDataModel(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService, "JobOfferService can't be null");
		this.jobOfferService = jobOfferService;
	}

	@Override
	protected AbstractService<JobOffer> getService() {
		return jobOfferService;
	}
}