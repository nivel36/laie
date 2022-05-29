package es.nivel36.laie.web.view.candidate;

import es.nivel36.laie.ejb.candidate.Candidate;

public class AddCandidatePermission implements CandidatePermission {

	@Override
	public boolean validate(Candidate entity) {
		return true;
	}
}
