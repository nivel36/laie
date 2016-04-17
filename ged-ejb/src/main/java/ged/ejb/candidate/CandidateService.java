package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CandidateService {

	public void deleteCandidate(Candidate candidate);

	public List<FileType> findAllFileTypes();

	public Candidate findById(final long id);

	public Candidate findCandidateAndCurriculumById(final long id);

	public Candidate findCandidateById(final long id);

	public List<Candidate> findCandidateByNameAndSurename(String name, String surename);

	public void insertCandidate(Candidate candidate);

	public Candidate updateCandidate(Candidate candidate);

}
