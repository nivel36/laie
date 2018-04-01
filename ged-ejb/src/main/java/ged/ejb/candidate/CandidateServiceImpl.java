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
import ged.ejb.core.Audited;
import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.tag.Tag;
import ged.ejb.job.offer.JobCandidature;
import ged.ejb.job.offer.JobCandidatureDao;

@Stateless
public class CandidateServiceImpl extends AbstratctAuditedService<Candidate> implements CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final CandidateDao candidateDao;

	private final JobCandidatureDao jobCandidatureDao;

	private final UploadedServerFileDao uploadedServerFileDao;

	@Inject
	public CandidateServiceImpl(@Repository final CandidateDao candidateDao, @Repository final UploadedServerFileDao uploadedServerFileDao,
			@Repository final JobCandidatureDao jobCandidatureDao) {
		Objects.requireNonNull(uploadedServerFileDao);
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
		this.uploadedServerFileDao = uploadedServerFileDao;
		this.jobCandidatureDao = jobCandidatureDao;
	}

	@Override
	public List<Candidate> findByJobOfferId(final long jobOfferId) {
		if (jobOfferId < 1) {
			throw new IllegalArgumentException("jobOfferId: " + jobOfferId);
		}
		logger.debug("Find candidates by jobOffer id {} ", jobOfferId);
		return this.candidateDao.findByJobOfferId(jobOfferId);
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
	public List<JobCandidature> findJobCandidatures(final long candidateId) {
		if (candidateId < 1) {
			throw new IllegalArgumentException();
		}
		return this.jobCandidatureDao.findByCandidateId(candidateId);
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
	public List<Tag> findTags() {
		logger.debug("Find all tags");
		return this.candidateDao.findTags();
	}

	@Override
	public Dao<Candidate> getDao() {
		return this.candidateDao;
	}

	@Override
	@Audited(action = ActionType.INSERT)
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

	private boolean isDuplicatedEmail(final Candidate candidate, final Candidate candidateInRepository) {
		return !candidate.getEmail().equals(candidateInRepository.getEmail()) && this.candidateDao.emailExists(candidate.getEmail());
	}

	@Override
	@Audited(action = ActionType.UPDATE)
	public Candidate update(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		final Candidate candidateInRepository = this.candidateDao.find(candidate.getId());
		if (this.isDuplicatedEmail(candidate, candidateInRepository)) {
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