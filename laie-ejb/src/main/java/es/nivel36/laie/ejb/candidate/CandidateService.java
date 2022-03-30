package es.nivel36.laie.ejb.candidate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.search.query.facet.Facet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.core.model.Page;
import es.nivel36.core.model.Repository;
import es.nivel36.core.model.search.SearchFacets;
import es.nivel36.core.model.search.SearchResult;
import es.nivel36.core.model.search.SortField;
import es.nivel36.files.FileDto;
import es.nivel36.files.FileService;
import es.nivel36.laie.ejb.core.tag.Tag;
import es.nivel36.laie.ejb.core.tag.TagDao;
import es.nivel36.laie.ejb.user.User;
import es.nivel36.laie.ejb.user.UserDao;

@Stateless
public class CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateService.class);

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@EJB
	private FileService fileService;

	@Inject
	@Repository
	private TagDao tagDao;

	@Inject
	@Repository
	private UserDao userDao;

	private CandidateMerger candidateMerger = new CandidateMerger();

	private CandidateMapper candidateMapper = new CandidateMapper(fileService);

	public CandidateDto addCandidate(final CandidateDto candidate, final String ownerUid) {
		Objects.requireNonNull(candidate);
		logger.debug("Add candidate {}", candidate);
		final Candidate entity = new Candidate();
		candidateMerger.merge(entity, candidate);
		this.changeOwner(entity, ownerUid);
		this.candidateDao.insert(entity);
		return candidateMapper.map(entity);
	}

	public void changeOwner(final String candidateUid, final String ownerUid) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(ownerUid);
		logger.debug("Cange owner {} to candidate {}", ownerUid, candidateUid);
		final Candidate candidate = this.candidateDao.findByUid(candidateUid);
		this.changeOwner(candidate, ownerUid);
	}

	private void changeOwner(final Candidate candidate, final String ownerUid) {
		final User user = this.userDao.findUserByUid(ownerUid);
		candidate.setOwner(user);
	}

	public void updateCandidate(final CandidateDto candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Update candidate {}", candidate);
		final String uid = candidate.getUid();
		final Candidate entity = this.candidateDao.findByUid(uid);
		this.updateTags(candidate.getTags(), entity.getTags());
		candidateMerger.merge(entity, candidate);
	}

	private void updateTags(final Set<String> tags, final Set<Tag> entityTags) {
		final Set<Tag> newTags = new HashSet<>();
		for (final String tagLabel : tags) {
			boolean tagExists = false;
			for (final Tag tag : entityTags) {
				final String label = tag.getLabel();
				if (tagLabel.equals(label)) {
					tagExists = true;
					break;
				}
			}
			if (tagExists) {
				continue;
			}
			final Tag tagFromDatabase = this.tagDao.findByLabel(tagLabel);
			if (tagFromDatabase != null) {
				newTags.add(tagFromDatabase);
			} else {
				final Tag newTag = new Tag();
				newTag.setLabel(tagLabel);
				newTags.add(newTag);
			}

		}
		entityTags.addAll(newTags);
	}

	public String changeCandidatesImage(final String candidateUid, final InputStream image) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(image);
		final Candidate candidate = this.candidateDao.findByUid(candidateUid);
		logger.debug("Change image to user {}", candidate);
		final String oldImage = candidate.getPictureUid();
		final String filename = candidateUid + "_picture";
		final FileDto newImage = this.fileService.uploadFile(image, filename, true);
		final String uid = newImage.getUid();
		candidate.setPictureUid(uid);
		if (oldImage != null) {
			logger.trace("Remove user {} old image", candidate);
			this.fileService.removeFile(oldImage);
		}
		return newImage.getPath();
	}

	public List<Origin> findCandidateOrigins() {
		logger.debug("Find all candidate origins");
		return this.candidateDao.findAllOrigins();
	}

	public List<CandidateDto> findCandidateByJobOffer(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(page);
		logger.debug("Find candidates by jobOffer {} ", jobOfferUid);
		final List<Candidate> candidates = this.candidateDao.findCandidates(jobOfferUid, page);
		return candidateMapper.mapList(candidates);
	}

	public CandidateDto findCandidateByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find candidate by uid {}", uid);
		final Candidate candidates = this.candidateDao.findByUid(uid);
		return candidateMapper.map(candidates);
	}

	public List<FileDto> findCandidatesFiles(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(page);
		logger.debug("Find files by candidate {}", candidateUid);
		final List<String> files = this.candidateDao.findCandidatesFiles(candidateUid, page);
		final List<FileDto> fileDtos = new ArrayList<FileDto>(files.size());
		for (final String file : files) {
			final FileDto fileDto = this.fileService.findByUid(file);
			fileDtos.add(fileDto);
		}
		return fileDtos;
	}

	public FileDto addFileToCandidate(final String candidateUid, final InputStream inputStream, String filename) {
		Objects.requireNonNull(inputStream);
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(filename);
		logger.debug("Add file {} to candidate {}", filename, candidateUid);
		final Candidate candidate = candidateDao.findCandidateWithFiles(candidateUid);
		final FileDto fileDto = fileService.uploadFile(inputStream, filename, false);
		candidate.addFile(fileDto.getUid());
		return fileDto;
	}

	public void removeFileFromCandidate(final String candidateUid, final String fileUid) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(fileUid);
		logger.debug("Remove file {} from candidate {}", fileUid, candidateUid);
		final Candidate candidate = this.candidateDao.findCandidateWithFiles(candidateUid);
		candidate.removeFile(fileUid);
		this.fileService.removeFile(fileUid);
	}

	public SearchResult<CandidateDto> search(final String searchText, final Page page) {
		return search(searchText, page, null, null);
	}

	public SearchResult<CandidateDto> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(page);
		logger.debug("Search {}, offset {} with limit of {}", searchText, page.getOffset(), page.getLimit());
		final SearchResult<Candidate> entities = this.candidateDao.search(searchText, page, sortOrder, searchFacets);
		final List<Candidate> resultData = entities.getResultData();
		final List<CandidateDto> dtoList = candidateMapper.mapList(resultData);
		final int count = entities.getCount();
		final Map<String, List<Facet>> allFacets = entities.getAllFacets();
		return new SearchResult<>(dtoList, count, allFacets);
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

	public void setUserDao(final UserDao userDao) {
		Objects.requireNonNull(userDao);
		this.userDao = userDao;
	}
}