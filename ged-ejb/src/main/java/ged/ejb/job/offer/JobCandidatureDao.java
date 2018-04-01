package ged.ejb.job.offer;

import java.util.List;

import ged.ejb.core.model.Dao;

public interface JobCandidatureDao extends Dao<JobCandidature> {

	List<JobCandidature> findByCandidateId(long candidateId);

	List<JobCandidature> findByJobOfferId(long jobOfferId);

	JobCandidature findByJobOfferIdAndCandidateId(long jobOfferId, long candidateId);
}