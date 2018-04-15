package ged.ejb.candidate;

import java.util.List;

import ged.ejb.ServerFile;
import ged.ejb.core.AuditedService;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobCandidature;

public interface CandidateService extends AuditedService<Candidate> {

	Candidate findAllDataById(long id);

	List<Candidate> findByJobOfferId(long jobOfferId);

	ServerFile findFile(long id);

	List<ServerFile> findFilesByCandidateId(long candidateId);

	List<JobCandidature> findJobCandidatures(long candidateId);

	List<Candidate> findLastAddedCandidates(int numberOfCandidates);

	long findNumberOfCandidates();

	List<Tag> findTags();

	void insertFile(ServerFile file);

	ServerFile updateFile(ServerFile file);
}