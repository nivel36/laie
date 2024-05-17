package es.nivel36.laie.ejb.candidate;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.search.engine.search.query.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.action.Create;
import es.nivel36.laie.ejb.core.action.Update;
import es.nivel36.laie.ejb.core.file.PhysicalFile;
import es.nivel36.laie.ejb.core.file.PhysicalFileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.core.tag.TagDao;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import es.nivel36.laie.ejb.user.DuplicateEmailException;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Stateless
public class CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateService.class);

	private @Inject CandidateDao candidateDao;
	private @Inject PhysicalFileService fileService;
	private @Inject TagDao tagDao;
	private @Inject FileDao fileDao;
	private @Inject @Create Event<Auditable> createCandidateEvent;
	private @Inject @Update Event<Auditable> updateCandidateEvent;

	public void addCandidate(final Candidate candidate) throws DuplicateEmailException {
		Objects.requireNonNull(candidate);
		final String email = candidate.getEmail();
		if (this.candidateDao.checkDuplicateEmail(email)) {
			throw new DuplicateEmailException("Email already exists: " + email);
		}
		logger.debug("Add candidate {}", candidate);
		this.updateTags(candidate);
		this.candidateDao.insert(candidate);
		this.createCandidateEvent.fireAsync(candidate);
	}

	private void updateTags(final Candidate candidate) {
		final Set<Tag> tags = candidate.getTags();
		final Set<Tag> normalizedTags = new HashSet<>(tags.size());
		for (final Tag tag : tags) {
			if (!tag.isNew()) {
				normalizedTags.add(tag);
				continue;
			}
			final Tag tagInDatabase = tagDao.findByLabel(tag.getLabel());
			if (tagInDatabase != null) {
				normalizedTags.add(tagInDatabase);
			} else {
				normalizedTags.add(tag);
			}
		}
		candidate.setTags(normalizedTags);
	}

	public Candidate updateCandidateRating(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Update rating of candidate {}", candidate);
		return updateAndFireEvent(candidate);
	}

	public Candidate updateCandidate(final Candidate candidate) throws DuplicateEmailException {
		Objects.requireNonNull(candidate);
		logger.debug("Update candidate {}", candidate);

		final Candidate candidateInDatabase = this.candidateDao.find(Candidate.class, candidate.getId());
		final String email = candidate.getEmail();
		if (!candidateInDatabase.getEmail().equals(email)) {
			if (this.candidateDao.checkDuplicateEmail(email)) {
				throw new DuplicateEmailException();
			}
		}
		this.updateTags(candidate);
		return updateAndFireEvent(candidate);
	}

	private Candidate updateAndFireEvent(final Candidate candidate) {
		final Candidate updatedCandidate = this.candidateDao.update(candidate);
		this.updateCandidateEvent.fireAsync(updatedCandidate);
		return updatedCandidate;
	}

	public Candidate changeCandidatesImage(final Candidate candidate, final InputStream image) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(image);
		logger.debug("Change image to user {}", candidate);
		final PhysicalFile oldImage = candidate.getPicture();
		final PhysicalFile newImage = this.fileService.uploadFile(image, true);
		candidate.setPicture(newImage);
		if (oldImage != null) {
			logger.trace("Remove user {} old image", candidate);
			this.fileService.removeFile(oldImage);
		}
		return updateAndFireEvent(candidate);
	}

	public List<Origin> findCandidateOrigins() {
		logger.debug("Find all candidate origins");
		return this.candidateDao.findAllOrigins();
	}

	public List<Candidate> findCandidateByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		logger.debug("Find candidates by jobOffer {} ", jobOffer);
		return this.candidateDao.findCandidatesByJobOffer(jobOffer, page);
	}

	public Candidate findCandidateByEmail(final String email) {
		Objects.requireNonNull(email);
		logger.debug("Find candidate by email {} ", email);
		return this.candidateDao.findCandidateByEmail(email);
	}

	public Candidate findCandidateById(final Long id) {
		Objects.requireNonNull(id);
		logger.debug("Find candidate by id {}", id);
		return this.candidateDao.find(Candidate.class, id);
	}

	public Candidate findAllCandidateData(final Long candidateId) {
		Objects.requireNonNull(candidateId);
		logger.debug("Find candidate data by id {}", candidateId);
		return this.candidateDao.findAllData(candidateId);
	}

	public List<File> findCandidateFiles(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		logger.debug("Find all files {} of candidate {}", candidate);
		return this.fileDao.findFilesByCandidate(candidate, page);
	}

	public long countCandidateFiles(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Count all files {} of candidate {}", candidate);
		return this.fileDao.countFilesByCandidate(candidate);
	}

	public void addCandidateFile(final Candidate candidate, final InputStream inputStream, String filename) {
		Objects.requireNonNull(inputStream);
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(filename);
		logger.debug("Add file {} to candidate {}", filename, candidate);
		final PhysicalFile physicalFile = fileService.uploadFile(inputStream, false);
		File file = new File();
		file.setCreated(LocalDateTime.now());
		file.setName(filename);
		file.setPublicAccess(false);
		file.setPhysicalFile(physicalFile);
		file.setCandidate(candidate);
		this.fileDao.insert(file);
		this.updateCandidateEvent.fireAsync(candidate);
	}

	public void deleteCandidateFile(final File file) {
		Objects.requireNonNull(file);
		final Candidate candidate = file.getCandidate();
		logger.debug("Remove file {} from candidate {}", file, candidate);
		this.fileService.removeFile(file.getPhysicalFile());
		this.fileDao.deleteFile(file);
		this.updateCandidateEvent.fireAsync(candidate);
	}

	public SearchResult<Candidate> search(final String searchText, final Page page) {
		return search(searchText, page, null, null);
	}

	public SearchResult<Candidate> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page);
		logger.debug("Search {}, offset {} with limit of {}", searchText, page.getOffset(), page.getLimit());
		return this.candidateDao.search(searchText, page, sortField, searchFacets);
	}

	public void setTagDao(final TagDao tagDao) {
		Objects.requireNonNull(tagDao);
		this.tagDao = tagDao;
	}

	public void setCandidateDao(final CandidateDao candidateDao) {
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
	}

	public void setFileService(final PhysicalFileService fileService) {
		Objects.requireNonNull(fileService);
		this.fileService = fileService;
	}
}