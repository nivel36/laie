package es.nivel36.laie.ejb.permissions;

import javax.inject.Inject;

import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserService;

public class AddCandidatureToJobOfferPermission extends AbstractJobOfferPermission {

	private @Inject UserService userService;

	@Override
	public boolean validate(final User user) {
		if (!jobOffer.isOpen()) {
			return false;
		}
		if (user.isAdmin()) {
			return true;
		}
		final User owner = jobOffer.getOwner();
		if (owner.equals(user)) {
			return true;
		}
		if (this.userService.isSubordinateUser(owner, user)) {
			return true;
		}
		if (this.jobOffer.getRecruiters().contains(user)) {
			return true;
		}
		return false;
	}

}
