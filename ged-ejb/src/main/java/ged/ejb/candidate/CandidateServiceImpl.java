package ged.ejb.candidate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.Audited;
import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.file.ServerFile;
import ged.ejb.core.file.ServerFileDao;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobCandidature;
import ged.ejb.job.offer.JobCandidatureDao;
import ged.ejb.job.offer.JobOffer;

@Stateless
public class CandidateServiceImpl extends AbstratctAuditedService<Candidate> implements CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private final CandidateDao candidateDao;

	private final JobCandidatureDao jobCandidatureDao;

	private final ServerFileDao serverFileDao;

	@Inject
	public CandidateServiceImpl(@Repository final CandidateDao candidateDao, @Repository final ServerFileDao uploadedServerFileDao,
			@Repository final JobCandidatureDao jobCandidatureDao) {
		Objects.requireNonNull(uploadedServerFileDao);
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
		this.serverFileDao = uploadedServerFileDao;
		this.jobCandidatureDao = jobCandidatureDao;
		logger.trace("CandidateServiceImpl initiated");
	}

	@Override
	public void addFileToCandidate(final Candidate candidate, final ServerFile file) {
		Objects.requireNonNull(file);
		Objects.requireNonNull(candidate);
		logger.debug("Adding file {} to candidate  {}", file, candidate);
		file.setCandidate(candidate);
		this.serverFileDao.insert(file);
	}

	@Override
	public Candidate findAllCandidateDataByCandidateId(final long candidateId) {
		if (candidateId < 1) {
			logger.error("Bad candidate id {}", candidateId);
			throw new IllegalArgumentException("Bad candidate id: " + candidateId);
		}
		logger.debug("Find all candidate data with id {}", candidateId);
		return this.candidateDao.findAllCandidateDataById(candidateId);
	}

	@Override
	public List<Candidate> findCandidatesByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Find candidates by jobOffer {} ", jobOffer);
		return this.candidateDao.findCandidatesByJobOffer(jobOffer);
	}

	@Override
	public ServerFile findFileByFileId(final long fileId) {
		if (fileId < 1) {
			logger.error("Bad file id {}", fileId);
			throw new IllegalArgumentException("Bad file id: " + fileId);
		}
		logger.debug("Find file by id {}", fileId);
		return this.serverFileDao.find(fileId);
	}

	@Override
	public List<ServerFile> findFilesByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find files by candidate {}", candidate);
		return this.serverFileDao.findByCandidate(candidate);
	}

	@Override
	public List<JobCandidature> findJobCandidaturesByCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find job candidatures by candidate {}", candidate);
		return this.jobCandidatureDao.findByCandidate(candidate);
	}

	@Override
	public List<Candidate> findLastAddedCandidates(final int numberOfCandidates) {
		if (numberOfCandidates < 1) {
			throw new IllegalArgumentException("numberOfCandidates: " + numberOfCandidates);
		}
		logger.debug("Find last added candidates");
		return this.candidateDao.findLastAddedCandidates(numberOfCandidates);
	}

	@Override
	public long findNumberOfCandidates() {
		logger.debug("Find total number of candidates");
		return this.candidateDao.findNumberOfCandidates();
	}

	@Override
	public Dao<Candidate> getDao() {
		return this.candidateDao;
	}

	@Override
	@Audited(action = ActionType.INSERT)
	public void insert(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Insert candidate {}", candidate);
		if (this.candidateDao.emailExists(candidate.getEmail())) {
			logger.warn("The email {} is in use", candidate.getEmail());
			throw new ValidationException("email");
		}
		this.candidateDao.insert(candidate);
	}

	private boolean isDuplicatedEmail(final Candidate candidate, final Candidate candidateInRepository) {
		return !candidate.getEmail().equals(candidateInRepository.getEmail()) && this.candidateDao.emailExists(candidate.getEmail());
	}

	@Override
	public void removeFile(final ServerFile file) {
		Objects.requireNonNull(file);
		logger.debug("Removing file {}", file);
		this.serverFileDao.delete(file);
	}

	@Override
	@Audited(action = ActionType.UPDATE)
	public Candidate update(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Update candidate {}", candidate);
		final Candidate candidateInRepository = this.candidateDao.find(candidate.getId());
		if (this.isDuplicatedEmail(candidate, candidateInRepository)) {
			logger.warn("The email {} is in use", candidate.getEmail());
			throw new ValidationException("Email duplicated");
		}
		return this.candidateDao.update(candidate);
	}

	@Override
	public ServerFile updateFile(final ServerFile file) {
		Objects.requireNonNull(file);
		logger.debug("Update file {}", file);
		return this.serverFileDao.update(file);
	}
}