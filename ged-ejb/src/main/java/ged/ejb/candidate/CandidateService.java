package ged.ejb.candidate;

import java.util.List;

import ged.ejb.UploadedServerFile;
import ged.ejb.core.AuditedService;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

public interface CandidateService extends AuditedService<Candidate> {

	List<Candidate> findAllByJobOffer(JobOffer jobOffer);

	List<Tag> findAllTags();

	Candidate findCandidateAndFiles(long id);

	UploadedServerFile findFile(final long id);

	List<Candidate> findLastAddedCandidates(final int numberOfCandidates);

	long findNumberOfCandidates();

	void insertFile(UploadedServerFile file);

	List<Candidate> search(List<String> searchValues);

	List<Candidate> search(String name, String surname, String position);

	List<Candidate> searchByNameAndSurname(String name, String surname, String position, boolean showDeleted);

	UploadedServerFile updateFile(UploadedServerFile file);
}