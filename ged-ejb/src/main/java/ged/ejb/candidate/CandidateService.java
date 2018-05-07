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

	ServerFile findFileByFileId(long fileId);

	List<ServerFile> findFilesByCandidate(Candidate candidate);

	List<JobCandidature> findJobCandidaturesByCandidate(Candidate candidate);

	List<Candidate> findLastAddedCandidates(int numberOfCandidates);

	long findNumberOfCandidates();

	void removeFile(ServerFile file);

	ServerFile updateFile(ServerFile file);
}