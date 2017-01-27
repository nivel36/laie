package ged.ejb.candidate;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobOffer;

@Stateless
public class CandidateServiceImpl extends AbstratctAuditedService<Long, Candidate> implements CandidateService {

	private static final Logger logger = Logger.getLogger(CandidateServiceImpl.class.getName());

	private final CandidateDao candidateDao;

	@Inject
	public CandidateServiceImpl(@Repository final CandidateDao candidateDao) {
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
	}

	@Override
	public List<Candidate> findAllByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.log(Level.FINE, "Search candidates by jobOffer {0} ", jobOffer.getId());
		return this.candidateDao.findAllByJobOffer(jobOffer);
	}

	@Override
	public List<FileType> findAllFileTypes() {
		logger.fine("Find all file types");
		return this.candidateDao.findAllFileTypes();
	}

	@Override
	public Candidate findCandidateAndFiles(final Long id) {
		Objects.requireNonNull(id);
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		logger.log(Level.FINE, "Find candidate with id {0} and his files", id);
		return this.candidateDao.findCandidateAndFiles(id);
	}

	@Override
	public Dao<Long, Candidate> getDao() {
		return this.candidateDao;
	}

	@Override
	public List<Candidate> search(final String name, final String surename, final String position) {
		logger.log(Level.FINE, "Search candidate by name {0} and surename {1}", new Object[] { name, surename });
		return this.candidateDao.searchByNameAndSurename(name, surename, position, null);
	}

	@Override
	public List<Candidate> searchByNameAndSurename(final String name, final String surename, final String position,
			final Boolean showDeleted) {
		logger.log(Level.FINE, "Search candidate by name {0} and surename {1}. Show deleteted {2}",
				new Object[] { name, surename, showDeleted });
		return this.candidateDao.searchByNameAndSurename(name, surename, position, showDeleted);
	}
}