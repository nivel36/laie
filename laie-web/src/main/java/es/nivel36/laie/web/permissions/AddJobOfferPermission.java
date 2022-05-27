package es.nivel36.laie.web.permissions;

import es.nivel36.laie.ejb.job.offer.JobOffer;

public class AddJobOfferPermission extends AbstractJobOfferPermission {

	@Override
	public boolean validate(JobOffer entity) {
		return true;
	}
}
