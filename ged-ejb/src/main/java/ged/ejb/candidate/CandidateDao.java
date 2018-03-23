package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.core.tag.Tag;

public interface CandidateDao extends Dao<Candidate> {

	boolean emailExists(String email);

	List<Candidate> findByJobOfferId(long jobOfferId);

	Candidate findCandidateAndFiles(long id);

	List<Candidate> findLastAddedCandidates(int numberOfCandidates);

	long findNumberOfCandidates();

	List<Tag> findTags();
}