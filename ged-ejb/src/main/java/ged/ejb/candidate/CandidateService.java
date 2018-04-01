package ged.ejb.candidate;

import java.util.List;

import ged.ejb.UploadedServerFile;
import ged.ejb.core.AuditedService;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobCandidature;

public interface CandidateService extends AuditedService<Candidate> {

	List<Candidate> findByJobOfferId(long jobOfferId);

	Candidate findCandidateAndFiles(long id);

	UploadedServerFile findFile(long id);

	List<JobCandidature> findJobCandidatures(long candidateId);

	List<Candidate> findLastAddedCandidates(int numberOfCandidates);

	long findNumberOfCandidates();

	List<Tag> findTags();

	void insertFile(UploadedServerFile file);

	UploadedServerFile updateFile(UploadedServerFile file);
}