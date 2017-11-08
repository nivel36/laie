package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.model.Dao;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

@Local
public interface CandidateDao extends Dao< Candidate> {

	List<Candidate> findAllByJobOffer(JobOffer jobOffer);

	Candidate findCandidateAndFiles(long id);

	List<Candidate> searchByNameAndSurename(String name, String surename, String position, boolean showDeleted);
	
	List<Tag> findAllTags();
}