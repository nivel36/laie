package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.job.offer.JobOffer;

public interface CandidateDao extends Dao<Candidate> {

	boolean emailExists(String email);

	Candidate findAllCandidateDataById(long candidateId);

	List<Candidate> findCandidatesByJobOffer(JobOffer jobOffer);

	List<Candidate> findLastAddedCandidates(int numberOfCandidates);

	long findNumberOfCandidates();
}