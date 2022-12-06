package es.nivel36.laie.ejb.candidate;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.action.Auditable;
import es.nivel36.laie.ejb.core.action.Create;
import es.nivel36.laie.ejb.core.action.Update;
import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
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

	private @Inject FileService fileService;

	private @Inject TagDao tagDao;

	private @Inject @Create Event<Auditable> createCandidateEvent;

	private @Inject @Update Event<Auditable> updateCandidateEvent;

	public void addCandidate(final Candidate candidate) throws DuplicateEmailException {
		Objects.requireNonNull(candidate);
		final String email = candidate.getEmail();
		if (this.candidateDao.checkDuplicateEmail(email)) {
			throw new DuplicateEmailException();
		}
		logger.debug("Add candidate {}", candidate);
		this.normalizeTags(candidate);
		this.candidateDao.insert(candidate);
		this.createCandidateEvent.fireAsync(candidate);
	}

	private void normalizeTags(final Candidate candidate) {
		final Set<Tag> tags = candidate.getTags();
		final Set<Tag> normalizedTags = new HashSet<>(tags.size());
		for (final Tag tag : tags) {
			final Tag tagInDatabase = tagDao.findByLabel(tag.getLabel());
			if (tagInDatabase != null) {
				normalizedTags.add(tagInDatabase);
			} else {
				normalizedTags.add(tag);
			}
		}
		candidate.setTags(normalizedTags);
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
		this.normalizeTags(candidate);
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
		final File oldImage = candidate.getPicture();
		final Long candidateId = candidate.getId();
		final String filename = candidateId + "_picture";
		final File newImage = this.fileService.uploadFile(image, filename, true);
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

	public Candidate addFileToCandidate(final Candidate candidate, final InputStream inputStream, String filename) {
		Objects.requireNonNull(inputStream);
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(filename);
		logger.debug("Add file {} to candidate {}", filename, candidate);
		final File file = fileService.uploadFile(inputStream, filename, false);
		candidate.addFile(file);
		return this.updateAndFireEvent(candidate);
	}
	
	public void addRating(final Candidate candidate, final Rating rating) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(rating);
		
		
	}

	public Candidate removeFileFromCandidate(final Candidate candidate, final File file) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(file);
		logger.debug("Remove file {} from candidate {}", file, candidate);
		candidate.removeFile(file);
		final Candidate updatedCandidate = this.candidateDao.update(candidate);
		this.fileService.removeFile(file);
		this.updateCandidateEvent.fireAsync(updatedCandidate);
		return updatedCandidate;
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
}