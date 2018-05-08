package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.candidate.Candidate;
import ged.ejb.core.model.Dao;

public interface JobCandidatureDao extends Dao<JobCandidature> {

	List<JobCandidature> findByCandidate(Candidate candidate);

	List<JobCandidature> findByJobOffer(JobOffer jobOffer);

	JobCandidature findByJobOfferAndCandidate(JobOffer jobOffer, Candidate candidate);
}