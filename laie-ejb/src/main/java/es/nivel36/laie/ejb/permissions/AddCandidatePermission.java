package es.nivel36.laie.ejb.permissions;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.user.User;

public class AddCandidatePermission extends AbstractCandidatePermission {

	@Override
	public boolean validate(Candidate entity, User user) {
		return true;
	}
}
