package es.nivel36.laie.ejb.permissions;

import es.nivel36.laie.ejb.candidate.Candidate;

public interface CandidatePermission extends Permission {

	void setCandidate(final Candidate candidata);

}
