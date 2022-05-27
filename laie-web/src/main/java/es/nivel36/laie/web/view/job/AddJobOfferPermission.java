package es.nivel36.laie.web.view.job;

import es.nivel36.laie.ejb.job.offer.JobOffer;

public class AddJobOfferPermission extends AbstractJobOfferPermission {

	@Override
	public boolean validate(JobOffer entity) {
		return true;
	}
}
