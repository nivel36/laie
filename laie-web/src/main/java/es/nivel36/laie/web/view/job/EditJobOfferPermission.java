package es.nivel36.laie.web.view.job;

import javax.inject.Inject;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.SessionUser;

public class EditJobOfferPermission extends AbstractJobOfferPermission {

	private @Inject SessionUser sessionUser;

	@Override
	public boolean validate(JobOffer jobOffer) {
		if (!jobOffer.isOpen()) {
			return false;
		}
		final User user = sessionUser.get();
		if (user.isAdmin()) {
			return true;
		}
		final User owner = jobOffer.getOwner();
		if (owner.equals(user)) {
			return true;
		}
		if (this.sessionUser.isManagerOf(owner)) {
			return true;
		}
		return false;
	}

}
