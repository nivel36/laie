package ged.ejb.candidate;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ged.ejb.core.AbstractIndexedService;
import ged.ejb.core.file.File;
import ged.ejb.core.file.FileService;
import ged.ejb.core.model.AbstractIndexedDao;
import ged.ejb.core.model.Page;
import ged.ejb.core.model.Repository;
import ged.ejb.job.candidature.JobCandidatureDao;
import ged.ejb.job.offer.JobOffer;

@Stateless
public class CandidateService extends AbstractIndexedService<Candidate> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	@Repository
	private FileService fileService;

	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;

	public Candidate addFileToCandidate(final Candidate candidate, final File file) {
		Objects.requireNonNull(file);
		Objects.requireNonNull(candidate);
		logger.debug("Add file {} to candidate  {}", file, candidate);
		final List<File> files = this.candidateDao.findFiles(candidate, Page.ALL_RESULTS);
		candidate.setFiles(files.stream().collect(Collectors.toSet()));
		candidate.addFile(file);
		return this.candidateDao.save(candidate);
	}

	public List<Origin> findAllOrigins() {
		logger.debug("Find all candidate origins");
		return this.candidateDao.findAllOrigins();
	}

	public Candidate findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find candidate by uid {}", uid);
		return this.candidateDao.findByUid(uid);
	}

	public List<Candidate> findCandidates(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Find candidates by jobOffer {} ", jobOffer);
		return this.candidateDao.findCandidates(jobOffer, page);
	}

	public File findFile(final long fileId) {
		if (fileId < 1) {
			logger.warn("Bad file id {}", fileId);
			throw new IllegalArgumentException("Bad file id: " + fileId);
		}
		logger.debug("Find file by id {}", fileId);
		return this.fileService.findById(fileId);
	}

	public List<File> findFiles(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		logger.debug("Find files by candidate {}", candidate);
		return this.candidateDao.findFiles(candidate, page);
	}

	@Override
	public AbstractIndexedDao<Candidate> getDao() {
		return this.candidateDao;
	}

	public void removeFile(Candidate candidate, final File file) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(file);
		logger.debug("Remove file {} from candidate {}", file, candidate);
		List<File> files = this.findFiles(candidate, Page.ALL_RESULTS);
		candidate.setFiles(new HashSet<>(files));
		candidate.removeFile(file);
		this.candidateDao.save(candidate);
		this.fileService.removeFile(file);
	}

	public void setCandidateDao(final CandidateDao candidateDao) {
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
	}
}