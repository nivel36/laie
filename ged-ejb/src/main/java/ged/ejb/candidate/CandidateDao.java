package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.model.Dao;
import ged.ejb.core.tag.Tag;

public interface CandidateDao extends Dao<Candidate> {

	boolean emailExists(String email);

	Candidate findAllDataById(long id);

	List<Candidate> findByJobOfferId(long jobOfferId);

	List<Candidate> findLastAddedCandidates(int numberOfCandidates);

	long findNumberOfCandidates();

	List<Tag> findTags();
}