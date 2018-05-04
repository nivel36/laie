package ged.ejb.candidate;

import java.util.List;

import ged.ejb.ServerFile;
import ged.ejb.core.AuditedService;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobCandidature;

public interface CandidateService extends AuditedService<Candidate> {

	void addFile(ServerFile file);

	Candidate findAllCandidateDataById(long candidateId);

	List<Tag> findAllTags();

	List<Candidate> findCandidatesByJobOfferId(long jobOfferId);

	List<ServerFile> findFilesByCandidateId(long candidateId);

	List<JobCandidature> findJobCandidaturesByCandidateId(long candidateId);

	ServerFile findFile(long fileId);

	List<Candidate> findLastAddedCandidates(int numberOfCandidates);

	long findNumberOfCandidates();

	void removeFile(ServerFile file);

	ServerFile updateFile(ServerFile file);
}