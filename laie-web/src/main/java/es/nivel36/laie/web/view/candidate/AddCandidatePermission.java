package es.nivel36.laie.web.view.candidate;

import es.nivel36.laie.ejb.candidate.Candidate;

public class AddCandidatePermission extends AbstractCandidatePermission {

	@Override
	public boolean validate(Candidate entity) {
		return true;
	}
}
