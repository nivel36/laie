package ged.ejb.job.offer;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.Dao;
import ged.ejb.job.JobCandidature;

public interface JobCandidatureDao extends Dao< JobCandidature> {

	public JobCandidature findByJobOfferAndCandidate(final JobOffer jobOffer, final Candidate candidate);

}
