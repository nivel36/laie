package es.nivel36.laie.ejb.permissions;

import es.nivel36.laie.ejb.candidate.Candidate;

public abstract class AbstractCandidatePermission implements CandidatePermission {

	protected Candidate candidate;

	@Override
	public void setCandidate(final Candidate candidate) {
		this.candidate = candidate;
	}
}
