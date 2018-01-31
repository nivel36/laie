package ged.ejb.candidate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.UploadedServerFile;
import ged.ejb.UploadedServerFileDao;
import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobOffer;

@Stateless
public class CandidateServiceImpl extends AbstratctAuditedService<Candidate> implements CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final CandidateDao candidateDao;

	private final UploadedServerFileDao uploadedServerFileDao;

	@Inject
	public CandidateServiceImpl(@Repository final CandidateDao candidateDao,
			@Repository final UploadedServerFileDao uploadedServerFileDao) {
		Objects.requireNonNull(uploadedServerFileDao);
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
		this.uploadedServerFileDao = uploadedServerFileDao;
	}

	@Override
	public List<Candidate> findAllByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Find candidates by jobOffer id {} ", jobOffer.getId());
		return this.candidateDao.findAllByJobOffer(jobOffer);
	}

	@Override
	public List<Tag> findAllTags() {
		logger.debug("Find all tags");
		return this.candidateDao.findAllTags();
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
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		return this.uploadedServerFileDao.find(id);
	}

	@Override
	public List<Candidate> findLastAddedCandidates(final int numberOfCandidates) {
		if (numberOfCandidates < 1) {
			throw new IllegalArgumentException("numberOfCandidates: " + numberOfCandidates);
		}
		return this.candidateDao.findLastAddedCandidates(numberOfCandidates);
	}

	@Override
	public long findNumberOfCandidates() {
		return this.candidateDao.findNumberOfCandidates();
	}

	@Override
	public Dao<Candidate> getDao() {
		return this.candidateDao;
	}

	@Override
	public void insert(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		if (this.candidateDao.emailExists(candidate.getEmail())) {
			throw new ValidationException("email");
		}
		this.candidateDao.insert(candidate);
	}

	@Override
	public void insertFile(final UploadedServerFile file) {
		Objects.requireNonNull(file);
		this.uploadedServerFileDao.insert(file);
	}

	@Override
	public Candidate update(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		final Candidate candidateInRepository = this.candidateDao.find(candidate.getId());
		if (!candidate.getEmail().equals(candidateInRepository.getEmail())
				&& this.candidateDao.emailExists(candidate.getEmail())) {
			throw new ValidationException("Email duplicated");
		}
		return this.candidateDao.update(candidate);
	}

	@Override
	public UploadedServerFile updateFile(final UploadedServerFile file) {
		Objects.requireNonNull(file);
		return this.uploadedServerFileDao.update(file);
	}
}