package es.nivel36.laie.ejb.permissions;

import es.nivel36.laie.ejb.user.User;

public class AddCandidateView extends AbstractCandidatePermission {

	@Override
	public boolean validate(User user) {
		return true;
	}

}
