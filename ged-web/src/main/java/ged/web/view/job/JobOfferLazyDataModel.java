package ged.web.view.job;

import java.util.Objects;

import ged.ejb.core.AbstractService;
import ged.ejb.job.offer.JobOffer;
import ged.ejb.job.offer.JobOfferService;
import ged.ejb.user.User;
import ged.web.core.view.AbstractLazyDataModel;

public class JobOfferLazyDataModel extends AbstractLazyDataModel<JobOffer> {

	private static final long serialVersionUID = 1L;

	private transient JobOfferService jobOfferService;

	private User user;

	public JobOfferLazyDataModel(final JobOfferService jobOfferService) {
		Objects.requireNonNull(jobOfferService, "JobOfferService can't be null");
		this.jobOfferService = jobOfferService;
	}

	@Override
	protected AbstractService<JobOffer> getService() {
		return this.jobOfferService;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}