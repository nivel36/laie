package ged.ejb.candidate;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

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

@Stateless
public class CandidateService extends AbstractIndexedService<Candidate> {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	private FileService fileService;

	@Inject
	@Repository
	private JobCandidatureDao jobCandidatureDao;

	public File addFileToCandidate(final String candidateUid, final InputStream inputStream, String filename) {
		Objects.requireNonNull(inputStream);
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(filename);
		final Candidate candidate = candidateDao.findCandidateWithFiles(candidateUid);
		logger.debug("Add file {} to candidate  {}", filename, candidate);
		final File file = fileService.uploadFile(inputStream, filename, false);
		candidate.addFile(file);
		return file;
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

	public List<Candidate> findByJobOffer(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(page);
		logger.debug("Find candidates by jobOffer {} ", jobOfferUid);
		return this.candidateDao.findCandidates(jobOfferUid, page);
	}

	public List<File> findCandidatesFiles(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		logger.debug("Find files by candidate {}", candidateUid);
		return this.candidateDao.findCandidatesFiles(candidateUid, page);
	}

	@Override
	public AbstractIndexedDao<Candidate> getDao() {
		return this.candidateDao;
	}

	public void removeFileFromCandidate(final String candidateUid, final File file) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(file);
		final Candidate candidate = this.candidateDao.findCandidateWithFiles(candidateUid);
		logger.debug("Remove file {} from candidate {}", file, candidate);
		candidate.removeFile(file);
		this.fileService.removeFile(file);
	}

	public void setCandidateDao(final CandidateDao candidateDao) {
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
	}
}