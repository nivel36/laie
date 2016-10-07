package ged.ejb.candidate;

import java.util.List;
import java.util.logging.Level;
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
		this.candidateDao = candidateDao;
	}

	@Override
	public List<FileType> findAllFileTypes() {
		logger.fine("Find all file types");
		return this.candidateDao.findAllFileTypes();
	}

	@Override
	public Candidate findCandidateAndFiles(final Long id) {
		if (id == null) {
			throw new NullPointerException();
		}
		logger.log(Level.FINE, "Find candidate with id {} and his files", id);
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