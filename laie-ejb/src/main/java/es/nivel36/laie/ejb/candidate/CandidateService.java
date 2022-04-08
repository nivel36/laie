package es.nivel36.laie.ejb.candidate;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileJpaDao;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.tag.TagDao;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateService.class);

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	private FileService fileService;

	@Inject
	@Repository
	private TagDao tagDao;

	@Inject
	@Repository
	private FileJpaDao fileDao;

	@Inject
	@Repository
	private UserDao userDao;

	public void addCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Add candidate {}", candidate);
		this.candidateDao.insert(candidate);
	}

	public Candidate updateCandidate(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Update candidate {}", candidate);
		return this.updateCandidate(candidate);
	}

	public String changeCandidatesImage(final Candidate candidate, final InputStream image) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(image);
		logger.debug("Change image to user {}", candidate);
		final File oldImage = candidate.getPicture();
		final Long candidateId = candidate.getId();
		final String filename = candidateId + "_picture";
		final File newImage = this.fileService.uploadFile(image, filename, true);
		candidate.setPicture(newImage);
		if (oldImage != null) {
			logger.trace("Remove user {} old image", candidate);
			this.fileService.removeFile(oldImage);
		}
		return newImage.getPhysicalFile().getRelativePath();
	}

	public List<Origin> findCandidateOrigins() {
		logger.debug("Find all candidate origins");
		return this.candidateDao.findAllOrigins();
	}

	public List<Candidate> findCandidateByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Find candidates by jobOffer {} ", jobOffer);
		return this.candidateDao.findCandidates(jobOffer, page);
	}

	public Candidate findCandidateById(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Find candidate by id {}", id);
		return this.candidateDao.find(Candidate.class,id);
	}

	public List<File> findCandidatesFiles(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		logger.debug("Find files by candidate {}", candidate);
		return this.candidateDao.findCandidatesFiles(candidate, page);
	}

	public File addFileToCandidate(final Candidate candidate, final InputStream inputStream, String filename) {
		Objects.requireNonNull(inputStream);
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(filename);
		logger.debug("Add file {} to candidate {}", filename, candidate);
		final File file = fileService.uploadFile(inputStream, filename, false);
		candidate.addFile(file);
		candidateDao.update(candidate);
		return file;
	}

	public void removeFileFromCandidate(final Candidate candidate, final File file) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(file);
		logger.debug("Remove file {} from candidate {}", file, candidate);
		final Candidate candidateWithFiles = this.candidateDao.findCandidateWithFiles(candidate.getId());
		candidateWithFiles.removeFile(file);
		this.fileService.removeFile(file);
		this.candidateDao.update(candidateWithFiles);
	}

	public SearchResult<Candidate> search(final String searchText, final Page page) {
		return search(searchText, page, null, null);
	}

	public SearchResult<Candidate> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(page);
		logger.debug("Search {}, offset {} with limit of {}", searchText, page.getOffset(), page.getLimit());
		return this.candidateDao.search(searchText, page, sortOrder, searchFacets);
	}

	public void setTagDao(final TagDao tagDao) {
		Objects.requireNonNull(tagDao);
		this.tagDao = tagDao;
	}

	public void setCandidateDao(final CandidateDao candidateDao) {
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
	}

	public void setFileService(final FileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}

	public void setFileDao(final FileJpaDao fileDao) {
		Objects.requireNonNull(fileDao);
		this.fileDao = fileDao;
	}

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}