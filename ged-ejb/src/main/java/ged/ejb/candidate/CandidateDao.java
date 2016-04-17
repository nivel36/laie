package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Local;

import ged.ejb.curriculum.Curriculum;

@Local
public interface CandidateDao {

	public void deleteCandidate(Candidate candidate);

	public List<FileType> findAllFileTypes();

	public Candidate findById(final long id);

	public Candidate findCandidateById(final long id);

	public List<Candidate> findCandidateByNameAndSurename(String name, String surename);

	public Curriculum findCurriculumByCandidateId(final long id);

	public void insertCandidate(Candidate candidate);

	public Candidate updateCandidate(Candidate candidate);

}
