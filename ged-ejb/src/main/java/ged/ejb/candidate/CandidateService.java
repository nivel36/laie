package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.AuditedService;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

public interface CandidateService extends AuditedService<Candidate> {

	List<Candidate> findAllByJobOffer(JobOffer jobOffer);

	Candidate findCandidateAndFiles(long id);

	List<Candidate> search(String name, String surename, String position);

	List<Candidate> searchByNameAndSurename(String name, String surename, String position, boolean showDeleted);
	
	List<Tag> findAllTags();
	
	UploadedServerFile updateFile(UploadedServerFile file);
	
	UploadedServerFile findFile(final long id);
	
	void insertFile(UploadedServerFile file);
}