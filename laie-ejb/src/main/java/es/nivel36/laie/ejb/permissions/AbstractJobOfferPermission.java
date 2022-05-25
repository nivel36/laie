package es.nivel36.laie.ejb.permissions;

import es.nivel36.laie.ejb.job.offer.JobOffer;

public abstract class AbstractJobOfferPermission implements JobOfferPermission {

	protected JobOffer jobOffer;

	public void setJobOffer(final JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}
}
