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
import jakarta.persistence.EntityNotFoundException;

/**
 * Stateless EJB service for managing Candidate entities.
 * <p>
 * Provides methods to add, update, search, and manage files and images
 * associated with Candidates, as well as tag normalization and event firing for
 * auditing purposes.
 */
@Stateless
public class CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateService.class);

	@Inject
	private CandidateDao candidateDao;
	@Inject
	private PhysicalFileService fileService;
	@Inject
	private TagDao tagDao;
	@Inject
	private FileDao fileDao;
	@Inject
	@Create
	private Event<Auditable> createCandidateEvent;
	@Inject
	@Update
	private Event<Auditable> updateCandidateEvent;

	/**
	 * Adds a new Candidate.
	 * <p>
	 * Validates that the Candidate is not null and email is unique, normalizes
	 * tags, inserts into DAO, fires creation event.
	 *
	 * @param candidate the {@link Candidate} to add; must not be null
	 * @throws NullPointerException    if candidate is null
	 * @throws DuplicateEmailException if email already exists
	 */
	public void addCandidate(final Candidate candidate) throws DuplicateEmailException {
		Objects.requireNonNull(candidate, "Candidate must not be null");
		final String email = candidate.getEmail();
		if (this.candidateDao.checkDuplicateEmail(email)) {
			throw new DuplicateEmailException("Email already exists: " + email);
		}
		logger.debug("Adding new Candidate: {}", candidate);
		this.updateTags(candidate);
		this.candidateDao.insert(candidate);
		this.createCandidateEvent.fire(candidate);
		logger.trace("Candidate {} added successfully.", candidate);
	}

	/**
	 * Normalizes Tag instances: reuses existing tags from database or keeps new
	 * ones.
	 *
	 * @param candidate the {@link Candidate} whose tags to normalize; must not be
	 *                  null
	 */
	private void updateTags(final Candidate candidate) {
		Objects.requireNonNull(candidate.getTags(), "Candidate tags must not be null");
		final Set<Tag> normalized = new HashSet<>();
		for (final Tag tag : candidate.getTags()) {
			if (!tag.isNew()) {
				normalized.add(tag);
			} else {
				final Tag persisted = this.tagDao.findByLabel(tag.getLabel());
				if (persisted != null) {
					logger.trace("Using existing Tag from database: {}", persisted);
					normalized.add(persisted);
				} else {
					logger.trace("Adding new Tag: {}", tag);
					normalized.add(tag);
				}
			}
		}
		candidate.setTags(normalized);
	}

	/**
	 * Updates the Candidate rating and fires update event.
	 *
	 * @param candidate the {@link Candidate} to update; must not be null
	 * @return the updated {@link Candidate}
	 * @throws NullPointerException if candidate is null
	 */
	public Candidate updateCandidateRating(final Candidate candidate) {
		Objects.requireNonNull(candidate, "Candidate must not be null");
		logger.debug("Updating rating for Candidate: {}", candidate);
		final Candidate updated = this.updateAndFireEvent(candidate);
		logger.trace("Candidate {} rating updated successfully.", updated);
		return updated;
	}

	private Candidate updateAndFireEvent(final Candidate candidate) {
		final Candidate updated = this.candidateDao.update(candidate);
		this.updateCandidateEvent.fire(updated);
		return updated;
	}

	/**
	 * Updates Candidate data.
	 * <p>
	 * Validates existence, email uniqueness if changed, normalizes tags, updates
	 * and fires event.
	 *
	 * @param candidate the {@link Candidate} to update; must not be null
	 * @return the updated {@link Candidate}
	 * @throws NullPointerException    if candidate is null
	 * @throws DuplicateEmailException if new email duplicates another
	 * @throws EntityNotFoundException if candidate not found in database
	 */
	public Candidate updateCandidate(final Candidate candidate) throws DuplicateEmailException {
		Objects.requireNonNull(candidate, "Candidate must not be null");
		logger.debug("Updating Candidate: {}", candidate);
		final Candidate existing = this.candidateDao.find(Candidate.class, candidate.getId());
		if (existing == null) {
			throw new EntityNotFoundException("Candidate not found: " + candidate.getId());
		}
		final String email = candidate.getEmail();
		if (!existing.getEmail().equals(email) && this.candidateDao.checkDuplicateEmail(email)) {
			throw new DuplicateEmailException("Email already exists: " + email);
		}
		this.updateTags(candidate);
		final Candidate updated = this.updateAndFireEvent(candidate);
		logger.trace("Candidate {} updated successfully.", updated);
		return updated;
	}

	/**
	 * Changes the Candidate's profile image.
	 * <p>
	 * Uploads new image, removes old one if present, updates entity and fires
	 * event.
	 *
	 * @param candidate the {@link Candidate} to update; must not be null
	 * @param image     the image InputStream to upload; must not be null
	 * @return the updated {@link Candidate}
	 * @throws NullPointerException if arguments are null
	 */
	public Candidate changeCandidateImage(final Candidate candidate, final InputStream image) {
		Objects.requireNonNull(candidate, "Candidate must not be null");
		Objects.requireNonNull(image, "Image InputStream must not be null");
		logger.debug("Changing image for Candidate: {}", candidate);
		final PhysicalFile old = candidate.getPicture();
		final PhysicalFile nxt = this.fileService.uploadFile(image, true);
		candidate.setPicture(nxt);
		if (old != null) {
			logger.trace("Removing old image for Candidate: {}", candidate);
			this.fileService.removeFile(old);
		}
		final Candidate updated = this.updateAndFireEvent(candidate);
		logger.trace("Candidate {} image changed successfully.", updated);
		return updated;
	}

	/**
	 * Retrieves all possible Candidate origins.
	 *
	 * @return list of {@link Origin} entities
	 */
	public List<Origin> findCandidateOrigins() {
		logger.debug("Finding all Candidate origins");
		final List<Origin> origins = this.candidateDao.findAllOrigins();
		logger.trace("Origins found: {}", origins);
		return origins;
	}

	/**
	 * Finds Candidates by JobOffer with pagination.
	 *
	 * @param jobOffer the {@link JobOffer} to filter by; must not be null
	 * @param page     the {@link Page} for pagination; must not be null
	 * @return list of matching {@link Candidate}
	 */
	public List<Candidate> findCandidateByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer, "JobOffer must not be null");
		Objects.requireNonNull(page, "Page must not be null");
		logger.debug("Finding Candidates for JobOffer: {}", jobOffer);
		final List<Candidate> list = this.candidateDao.findCandidatesByJobOffer(jobOffer, page);
		logger.trace("Candidates found for JobOffer {}: {}", jobOffer, list);
		return list;
	}

	/**
	 * Finds a Candidate by email.
	 *
	 * @param email the email to search; must not be null
	 * @return the matching {@link Candidate}, or null if not found
	 */
	public Candidate findCandidateByEmail(final String email) {
		Objects.requireNonNull(email, "Email must not be null");
		logger.debug("Finding Candidate by email: {}", email);
		final Candidate cand = this.candidateDao.findCandidateByEmail(email);
		logger.trace("Candidate {} found for email {}.", cand, email);
		return cand;
	}

	/**
	 * Finds a Candidate by ID.
	 *
	 * @param candidateId the ID to search; must not be null
	 * @return the matching {@link Candidate}, or null if not found
	 */
	public Candidate findCandidateById(final long candidateId) {
		logger.debug("Finding Candidate by ID: {}", candidateId);
		final Candidate cand = this.candidateDao.find(Candidate.class, candidateId);
		logger.trace("Candidate {} found for ID {}.", cand, candidateId);
		return cand;
	}

	/**
	 * Retrieves all data for a Candidate, including any related entities (e.g.
	 * tags, files, origins).
	 *
	 * @param candidateId the unique identifier of the Candidate; must not be null
	 * @return the {@link Candidate} populated with full data, or null if not found
	 * @throws NullPointerException if candidateId is null
	 */
	public Candidate findAllCandidateData(final long candidateId) {
		logger.debug("Finding all Candidate data for ID: {}", candidateId);
		final Candidate cand = this.candidateDao.findAllData(candidateId);
		logger.trace("Full Candidate data {} retrieved for ID {}.", cand, candidateId);
		return cand;
	}

	/**
	 * Retrieves the paginated list of files attached to a given Candidate.
	 *
	 * @param candidate the {@link Candidate} whose files are to be retrieved; must
	 *                  not be null
	 * @param page      the pagination parameters; must not be null
	 * @return a {@link List} of {@link File} instances belonging to the Candidate,
	 *         possibly empty but never null
	 * @throws NullPointerException if either argument is null
	 */
	public List<es.nivel36.laie.ejb.candidate.File> findCandidateFiles(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate, "Candidate must not be null");
		Objects.requireNonNull(page, "Page must not be null");
		logger.debug("Finding files for Candidate: {}", candidate);
		final List<es.nivel36.laie.ejb.candidate.File> files = this.fileDao.findFilesByCandidate(candidate, page);
		logger.trace("Files {} retrieved for Candidate {}.", files, candidate);
		return files;
	}

	/**
	 * Counts the total number of files attached to a given Candidate.
	 *
	 * @param candidate the {@link Candidate} whose files are to be counted; must
	 *                  not be null
	 * @return the total number of files associated with the Candidate
	 * @throws NullPointerException if candidate is null
	 */
	public long countCandidateFiles(final Candidate candidate) {
		Objects.requireNonNull(candidate, "Candidate must not be null");
		logger.debug("Counting files for Candidate: {}", candidate);
		final long count = this.fileDao.countFilesByCandidate(candidate);
		logger.trace("Counted {} files for Candidate {}.", count, candidate);
		return count;
	}

	/**
	 * Adds a new file to a Candidate’s profile.
	 *
	 * @param candidate   the {@link Candidate} to receive the new file; must not be
	 *                    null
	 * @param inputStream the {@link InputStream} of the file content; must not be
	 *                    null
	 * @param filename    the original filename; must not be null or empty
	 * @throws NullPointerException if any argument is null
	 */
	public void addCandidateFile(final Candidate candidate, final InputStream inputStream, final String filename) {
		Objects.requireNonNull(candidate, "Candidate must not be null");
		Objects.requireNonNull(inputStream, "InputStream must not be null");
		Objects.requireNonNull(filename, "Filename must not be null");
		logger.debug("Adding file '{}' to Candidate: {}", filename, candidate);
		final PhysicalFile pf = this.fileService.uploadFile(inputStream, false);
		final es.nivel36.laie.ejb.candidate.File file = new es.nivel36.laie.ejb.candidate.File();
		file.setCreated(LocalDateTime.now());
		file.setName(filename);
		file.setPublicAccess(false);
		file.setPhysicalFile(pf);
		file.setCandidate(candidate);
		this.fileDao.insert(file);
		candidate.getFiles().add(file);
		this.candidateDao.update(candidate);
		this.updateCandidateEvent.fire(candidate);
		logger.trace("File '{}' added successfully to Candidate {}.", filename, candidate);
	}

	/**
	 * Deletes an existing file from a Candidate’s profile.
	 *
	 * @param file the {@link File} entity to delete; must not be null
	 * @throws NullPointerException if file is null
	 */
	public void deleteCandidateFile(final es.nivel36.laie.ejb.candidate.File file) {
		Objects.requireNonNull(file, "File must not be null");
		final Candidate candidate = file.getCandidate();
		logger.debug("Deleting file '{}' from Candidate: {}", file.getName(), candidate);
		this.fileService.removeFile(file.getPhysicalFile());
		this.fileDao.deleteFile(file);
		this.updateCandidateEvent.fire(candidate);
		logger.trace("File '{}' deleted successfully from Candidate {}.", file.getName(), candidate);
	}

	/**
	 * Searches for Candidates matching the given text, with optional pagination,
	 * sorting, and facet filters.
	 *
	 * @param searchText   the free‑text query; may be null or empty to match all
	 * @param page         the pagination settings; must not be null
	 * @param sortField    the field by which to sort; may be null for default order
	 * @param searchFacets an array of facet strings to filter by; may be null
	 * @return a {@link SearchResult} containing matching Candidates and metadata
	 * @throws NullPointerException if page is null
	 */
	public SearchResult<Candidate> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		Objects.requireNonNull(page, "Page must not be null");
		logger.debug("Searching Candidates with text: '{}', offset: {}, limit: {}", searchText, page.getOffset(),
				page.getLimit());
		final SearchResult<Candidate> res = this.candidateDao.search(searchText, page, sortField, searchFacets);
		logger.trace("Search completed for text '{}' with results {}.", searchText, res);
		return res;
	}

	/**
	 * Sets the TagDao instance, primarily for testing purposes.
	 *
	 * @param tagDao the {@link TagDao} to set; must not be null
	 * @throws NullPointerException if tagDao is null
	 */
	public void setTagDao(final TagDao tagDao) {
		this.tagDao = Objects.requireNonNull(tagDao, "TagDao must not be null");
	}

	/**
	 * Sets the CandidateDao instance, primarily for testing purposes.
	 *
	 * @param candidateDao the {@link CandidateDao} to set; must not be null
	 * @throws NullPointerException if candidateDao is null
	 */
	public void setCandidateDao(final CandidateDao candidateDao) {
		this.candidateDao = Objects.requireNonNull(candidateDao, "CandidateDao must not be null");
	}

	/**
	 * Sets the FileDao instance, primarily for testing purposes.
	 *
	 * @param fileDao the {@link FileDao} to set; must not be null
	 * @throws NullPointerException if fileDao is null
	 */
	public void setFileDao(final FileDao fileDao) {
		this.fileDao = Objects.requireNonNull(fileDao, "FileDao must not be null");
	}

	/**
	 * Sets the PhysicalFileService instance, primarily for testing purposes.
	 *
	 * @param fileService the {@link PhysicalFileService} to set; must not be null
	 * @throws NullPointerException if fileService is null
	 */
	public void setFileService(final PhysicalFileService fileService) {
		this.fileService = Objects.requireNonNull(fileService, "FileService must not be null");
	}

	/**
	 * Sets the Event used for create-candidate auditing, primarily for testing
	 * purposes.
	 *
	 * @param createCandidateEvent the {@link Event}{@code <Auditable>} to set; must
	 *                             not be null
	 * @throws NullPointerException if createCandidateEvent is null
	 */
	public void setCreateCandidateEvent(final Event<Auditable> createCandidateEvent) {
		this.createCandidateEvent = Objects.requireNonNull(createCandidateEvent, "CreateEvent must not be null");
	}

	/**
	 * Sets the Event used for update-candidate auditing, primarily for testing
	 * purposes.
	 *
	 * @param updateCandidateEvent the {@link Event}{@code <Auditable>} to set; must
	 *                             not be null
	 * @throws NullPointerException if updateCandidateEvent is null
	 */
	public void setUpdateCandidateEvent(final Event<Auditable> updateCandidateEvent) {
		this.updateCandidateEvent = Objects.requireNonNull(updateCandidateEvent, "UpdateEvent must not be null");
	}
}
