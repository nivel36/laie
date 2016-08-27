package ged.ejb.candidate;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

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
		return this.candidateDao.findByNameAndSurename(name, surename, null);
	}

	@Override
	public List<Candidate> findByNameAndSurename(final String name, final String surename, final Boolean showDeleted) {
		return this.candidateDao.findByNameAndSurename(name, surename, showDeleted);
	}

	@Override
	public Candidate findCandidateAndFiles(final Long id) {
		return this.candidateDao.findCandidateAndFiles(id);
	}

	@Override
	public Dao<Long, Candidate> getDao() {
		return this.candidateDao;
	}
}