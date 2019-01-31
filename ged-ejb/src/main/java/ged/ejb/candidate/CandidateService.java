package ged.ejb.candidate;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractAuditedService;
import ged.ejb.core.file.ServerFile;
import ged.ejb.core.file.ServerFileDao;
import ged.ejb.core.model.AbstractDao;
import ged.ejb.core.model.Repository;
import ged.ejb.job.offer.JobCandidature;
import ged.ejb.job.offer.JobCandidatureDao;
import ged.ejb.job.offer.JobOffer;

@Stateless
public class CandidateService extends AbstractAuditedService<Candidate> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;

	@Inject
	@Repository
	private ServerFileDao serverFileDao;

	public void addFileToCandidate(final Candidate candidate, final ServerFile file) {
		Objects.requireNonNull(file);
		Objects.requireNonNull(candidate);
		logger.debug("Adding file {} to candidate  {}", file, candidate);
		file.setCandidate(candidate);
		this.serverFileDao.save(file);
	}

	public List<Origin> findAllOrigins() {
		return this.candidateDao.findAllOrigins();
	}

	public Candidate findCandidateData(final long candidateId) {
		if (candidateId < 1) {
			logger.warn("Bad candidate id {}", candidateId);
			throw new IllegalArgumentException("Bad candidate id: " + candidateId);
		}
		logger.debug("Find all candidate data with id {}", candidateId);
		return this.candidateDao.findCandidateData(candidateId);
	}

	public List<Candidate> findCandidates(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		logger.debug("Find candidates by jobOffer {} ", jobOffer);
		return this.candidateDao.findCandidates(jobOffer);
	}

	public ServerFile findFile(final long fileId) {
		if (fileId < 1) {
			logger.warn("Bad file id {}", fileId);
			throw new IllegalArgumentException("Bad file id: " + fileId);
		}
		logger.debug("Find file by id {}", fileId);
		return this.serverFileDao.find(fileId);
	}

	public List<ServerFile> findFiles(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find files by candidate {}", candidate);
		return this.serverFileDao.findByCandidate(candidate);
	}

	public List<JobCandidature> findJobCandidatures(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Find job candidatures by candidate {}", candidate);
		return this.jobCandidatureDao.findByCandidate(candidate);
	}

	public List<Candidate> findLastAddedCandidates(final int numberOfCandidates) {
		if (numberOfCandidates < 1) {
			throw new IllegalArgumentException("numberOfCandidates: " + numberOfCandidates);
		}
		logger.debug("Find last added candidates");
		return this.candidateDao.findLastAddedCandidates(numberOfCandidates);
	}

	public long findNumberOfCandidates() {
		logger.debug("Find total number of candidates");
		return this.candidateDao.findNumberOfCandidates();
	}

	@Override
	public AbstractDao<Candidate> getDao() {
		return this.candidateDao;
	}

	public void removeFile(final ServerFile file) {
		Objects.requireNonNull(file);
		logger.debug("Removing file {}", file);
		this.serverFileDao.delete(file);
	}

	public void setCandidateDao(final CandidateDao candidateDao) {
		this.candidateDao = candidateDao;
	}

	public void setJobCandidatureDao(final JobCandidatureDao jobCandidatureDao) {
		this.jobCandidatureDao = jobCandidatureDao;
	}

	public void setServerFileDao(final ServerFileDao serverFileDao) {
		this.serverFileDao = serverFileDao;
	}

	public ServerFile updateFile(final ServerFile file) {
		Objects.requireNonNull(file);
		logger.debug("Update file {}", file);
		return this.serverFileDao.save(file);
	}
}