package ged.ejb.candidate.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.candidate.Candidate;
import ged.ejb.candidate.CandidateDao;
import ged.ejb.candidate.CandidateService;
import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.curriculum.Curriculum;

@Stateless
public class CandidateServiceImpl extends AbstratctAuditedService<Candidate> implements CandidateService {

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Override
	public List<FileType> findAllFileTypes() {
		return this.candidateDao.findAllFileTypes();
	}

	@Override
	public List<Candidate> findByNameAndSurename(final String name, final String surename) {
		return this.candidateDao.findByNameAndSurename(name, surename, false);
	}

	@Override
	public List<Candidate> findByNameAndSurename(final String name, final String surename, final boolean showDeleted) {
		return this.candidateDao.findByNameAndSurename(name, surename, showDeleted);
	}

	@Override
	public Curriculum findCurriculumByCandidateId(final long id) {
		return this.candidateDao.findCurriculumByCandidateId(id);
	}

	@Override
	public Dao<Long, Candidate> getDao() {
		return this.candidateDao;
	}
}