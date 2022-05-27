package es.nivel36.laie.web.view.candidate;

import javax.inject.Inject;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.web.core.view.SessionUser;

public class EditCandidatePermission extends AbstractCandidatePermission {

	private @Inject SessionUser sessionUser;

	@Override
	public boolean validate(final Candidate candidate) {
		final User user = sessionUser.get();
		if (user.isAdmin()) {
			return true;
		}
		final User owner = candidate.getOwner();
		if (owner.equals(user)) {
			return true;
		}
		if (sessionUser.isManagerOf(owner)) {
			return true;
		}
		return false;
	}
}
