package es.nivel36.laie.ejb.permissions;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;

public class AddJobOfferPermission extends AbstractJobOfferPermission {

	@Override
	public boolean validate(JobOffer entity, User user) {
		return true;
	}
}
