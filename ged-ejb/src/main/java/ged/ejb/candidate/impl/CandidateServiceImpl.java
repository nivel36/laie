package ged.ejb.candidate.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateDao;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.FileType;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.Curriculum;

@Stateless
public class CandidateServiceImpl implements CandidateService {

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Override
	public void deleteCandidate(final Candidate candidate) {
		this.candidateDao.deleteCandidate(candidate);
	}

	@Override
	public List<FileType> findAllFileTypes() {
		return this.candidateDao.findAllFileTypes();
	}

	@Override
	public Candidate findById(final long id) {
		return this.candidateDao.findById(id);
	}

	@Override
	public Candidate findCandidateById(final long id) {
		return this.candidateDao.findCandidateById(id);
	}

	@Override
	public List<Candidate> findCandidateByNameAndSurename(final String name, final String surename) {
		return this.candidateDao.findCandidateByNameAndSurename(name, surename);
	}

	@Override
	public Curriculum findCurriculumByCandidateId(final long id) {
		return this.candidateDao.findCurriculumByCandidateId(id);
	}

	@Override
	public void insertCandidate(final Candidate candidate) {
		this.candidateDao.insertCandidate(candidate);

	}

	@Override
	public Candidate updateCandidate(final Candidate candidate) {
		return this.candidateDao.updateCandidate(candidate);
	}
}
