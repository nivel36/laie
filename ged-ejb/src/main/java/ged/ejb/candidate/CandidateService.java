package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.AuditedService;
import ged.ejb.core.FileType;
import ged.ejb.job.offer.JobOffer;

public interface CandidateService extends AuditedService<Candidate> {

	List<Candidate> findAllByJobOffer(JobOffer jobOffer);

	List<FileType> findAllFileTypes();

	Candidate findCandidateAndFiles(long id);

	List<Candidate> search(String name, String surename, String position);

	List<Candidate> searchByNameAndSurename(String name, String surename, String position, Boolean showDeleted);
}