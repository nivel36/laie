package es.nivel36.laie.web.permissions;

import es.nivel36.laie.ejb.candidate.Candidate;

public class AddCandidatePermission extends AbstractCandidatePermission {

	@Override
	public boolean validate(Candidate entity) {
		return true;
	}
}
