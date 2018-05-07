package ged.ejb.candidate;

import java.util.List;

import ged.ejb.core.AuditedService;
import ged.ejb.core.file.ServerFile;
import ged.ejb.job.offer.JobCandidature;
import ged.ejb.job.offer.JobOffer;

public interface CandidateService extends AuditedService<Candidate> {

	void addFileToCandidate(Candidate candidate, ServerFile file);

	Candidate findAllCandidateDataByCandidateId(long candidateId);

	List<Candidate> findCandidatesByJobOffer(JobOffer jobOffer);

	ServerFile findFile(long fileId);

	List<ServerFile> findFilesByCandidateId(long candidateId);

	List<JobCandidature> findJobCandidaturesByCandidateId(long candidateId);

	List<Candidate> findLastAddedCandidates(int numberOfCandidates);

	long findNumberOfCandidates();

	void removeFile(ServerFile file);

	ServerFile updateFile(ServerFile file);
}