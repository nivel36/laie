package ged.ejb.candidate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.FileType;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

@Stateless
public class CandidateServiceImpl extends AbstratctAuditedService<Candidate> implements CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final CandidateDao candidateDao;

	private final FileSysDao fileSysDao;

	@Inject
	public CandidateServiceImpl(@Repository final CandidateDao candidateDao, @Repository final FileSysDao fileSysDao) {
		Objects.requireNonNull(fileSysDao);
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
		this.fileSysDao = fileSysDao;
	}

	@Override
	public List<Candidate> findAllByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Find candidates by jobOffer id {} ", jobOffer.getId());
		return this.candidateDao.findAllByJobOffer(jobOffer);
	}

	@Override
	public List<FileType> findAllFileTypes() {
		logger.debug("Find all file types");
		return this.candidateDao.findAllFileTypes();
	}

	@Override
	public Candidate findCandidateAndFiles(final long id) {
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		logger.debug("Find candidate with id {} and his/her files", id);
		return this.candidateDao.findCandidateAndFiles(id);
	}

	@Override
	public UploadedServerFile findFile(final long id) {
		return this.fileSysDao.find(id);
	}

	@Override
	public Dao<Candidate> getDao() {
		return this.candidateDao;
	}

	@Override
	public List<Candidate> search(final String name, final String surename, final String position) {
		logger.debug("Search candidate by name {} and surename {}", name, surename);
		return this.candidateDao.searchByNameAndSurename(name, surename, position, false);
	}

	@Override
	public List<Candidate> searchByNameAndSurename(final String name, final String surename, final String position,
			final boolean showDeleted) {
		logger.debug("Search candidate by name {} and surename {}. Show deleteted {}", name, surename, showDeleted);
		return this.candidateDao.searchByNameAndSurename(name, surename, position, showDeleted);
	}

	@Override
	public List<Tag> findAllTags() {
		logger.debug("Find all tags");
		return candidateDao.findAllTags();
	}

	@Override
	public UploadedServerFile upddateFile(final UploadedServerFile file) {
		return this.fileSysDao.update(file);
	}
}