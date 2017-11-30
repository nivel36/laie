package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.model.Dao;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

@Local
public interface CandidateDao extends Dao<Candidate> {

	boolean emailExists(String email);

	List<Candidate> findAllByJobOffer(JobOffer jobOffer);

	List<Tag> findAllTags();

	Candidate findCandidateAndFiles(final long id);

	List<Candidate> findLastAddedCandidates(final int numberOfCandidates);

	long findNumberOfCandidates();

	List<Candidate> search(final List<String> searchValues);

	List<Candidate> searchByNameAndSurename(String name, String surename, String position, boolean showDeleted);
}