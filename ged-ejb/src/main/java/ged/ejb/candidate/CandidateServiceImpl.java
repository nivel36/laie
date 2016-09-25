package ged.ejb.candidate;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;

@Stateless
public class CandidateServiceImpl extends AbstratctAuditedService<Candidate> implements CandidateService {

	private static final Logger logger = Logger.getLogger(CandidateServiceImpl.class.getName());

	private CandidateDao candidateDao;

	@Inject
	public CandidateServiceImpl(@Repository final CandidateDao candidateDao) {
		super();
		this.candidateDao = candidateDao;
	}

	@Override
	public List<FileType> findAllFileTypes() {
		return this.candidateDao.findAllFileTypes();
	}

	@Override
	public Candidate findCandidateAndFiles(final Long id) {
		return this.candidateDao.findCandidateAndFiles(id);
	}

	@Override
	public Dao<Long, Candidate> getDao() {
		return this.candidateDao;
	}

	@Override
	public List<Candidate> searchByNameAndSurename(final String name, final String surename) {
		return this.candidateDao.searchByNameAndSurename(name, surename, null);
	}

	@Override
	public List<Candidate> searchByNameAndSurename(final String name, final String surename,
			final Boolean showDeleted) {
		return this.candidateDao.searchByNameAndSurename(name, surename, showDeleted);
	}

	public void setCandidateDao(final CandidateDao candidateDao) {
		this.candidateDao = candidateDao;
	}
}