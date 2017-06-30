package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;
import ged.ejb.job.offer.JobOffer;

@Local
public interface CandidateDao extends Dao< Candidate> {

	List<Candidate> findAllByJobOffer(JobOffer jobOffer);

	List<FileType> findAllFileTypes();

	Candidate findCandidateAndFiles(long id);

	List<Candidate> searchByNameAndSurename(String name, String surename, String position, Boolean showDeleted);
}