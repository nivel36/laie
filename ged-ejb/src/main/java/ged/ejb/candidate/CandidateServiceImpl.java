package ged.ejb.candidate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.ServerFile;
import ged.ejb.ServerFileDao;
import ged.ejb.core.AbstratctAuditedService;
import ged.ejb.core.Audited;
import ged.ejb.core.action.Action.ActionType;
import ged.ejb.core.model.Dao;
import ged.ejb.core.model.Repository;
import ged.ejb.core.tag.Tag;
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
	}

	@Override
	public void addFile(final ServerFile file) {
		Objects.requireNonNull(file);
		this.serverFileDao.insert(file);
	}

	@Override
	public Candidate findAllCandidateDataById(final long candidateId) {
		if (candidateId < 1) {
			throw new IllegalArgumentException("id: " + candidateId);
		}
		logger.debug("Find candidate with id {} and his/her files", candidateId);
		return this.candidateDao.findAllCandidateDataById(candidateId);
	}

	@Override
	public List<Tag> findAllTags() {
		logger.debug("Find all tags");
		return this.candidateDao.findTags();
	}

	@Override
	public List<Candidate> findCandidatesByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Find candidates by jobOffer {} ", jobOffer);
		return this.candidateDao.findCandidatesByJobOffer(jobOffer);
	}

	@Override
	public ServerFile findFile(final long id) {
		if (id < 1) {
			throw new IllegalArgumentException("id: " + id);
		}
		return this.serverFileDao.find(id);
	}

	@Override
	public List<ServerFile> findFilesByCandidateId(final long candidateId) {
		if (candidateId < 1) {
			throw new IllegalArgumentException("candidateId: " + candidateId);
		}
		return this.serverFileDao.findByCandidateId(candidateId);
	}

	@Override
	public List<JobCandidature> findJobCandidaturesByCandidateId(final long candidateId) {
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

	private boolean isDuplicatedEmail(final Candidate candidate, final Candidate candidateInRepository) {
		return !candidate.getEmail().equals(candidateInRepository.getEmail()) && this.candidateDao.emailExists(candidate.getEmail());
	}

	@Override
	public void removeFile(final ServerFile file) {
		this.serverFileDao.delete(file);
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
	public ServerFile updateFile(final ServerFile file) {
		Objects.requireNonNull(file);
		return this.serverFileDao.update(file);
	}
}