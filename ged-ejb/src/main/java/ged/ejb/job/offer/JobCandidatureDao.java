package ged.ejb.job.offer;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.Dao;

public interface JobCandidatureDao extends Dao< JobCandidature> {

	public JobCandidature findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate);

}
