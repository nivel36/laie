package es.nivel36.laie.web.view.job;

import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.SessionUser;
import jakarta.inject.Inject;

public class EditJobOfferPermission extends AbstractJobOfferPermission {

	private @Inject SessionUser sessionUser;

	@Override
	public boolean validate(JobOffer jobOffer) {
		if (!jobOffer.isOpen()) {
			return false;
		}
		final User user = sessionUser.get();
		if (sessionUser.isAdmin()) {
			return true;
		}
		final User owner = jobOffer.getOwner();
		if (owner.equals(user) || this.sessionUser.isManagerOf(owner)) {
			return true;
		}
		return false;
	}

}
