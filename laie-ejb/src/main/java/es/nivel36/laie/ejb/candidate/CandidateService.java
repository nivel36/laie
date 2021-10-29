package es.nivel36.laie.ejb.candidate;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.search.query.facet.Facet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.file.FileDto;
import es.nivel36.laie.ejb.core.file.FileJpaDao;
import es.nivel36.laie.ejb.core.file.FileService;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;

@Stateless
public class CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(Candidate.class);

	@Inject
	@Repository
	private CandidateDao candidateDao;

	@Inject
	private FileService fileService;

	@Inject
	@Repository
	private FileJpaDao fileDao;

	private CandidateMerger candidateMerger;

	private CandidateMapper candidateMapper;

	@PostConstruct
	public void init() {
		candidateMerger = new CandidateMerger();
		candidateMapper = new CandidateMapper();
	}

	public String changeUsersImage(final String candidateUid, final InputStream image) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(image);
		final Candidate candidate = this.candidateDao.findByUid(candidateUid);
		logger.debug("Change image to user {}", candidate);
		final File oldImage = candidate.getPicture();
		final String filename = candidateUid + "_picture";
		final FileDto newImage = this.fileService.uploadFile(image, filename, true);
		final String uid = newImage.getUid();
		final File file = fileDao.findFileByUid(uid);
		candidate.setPicture(file);
		if (oldImage != null) {
			logger.trace("Remove user {} old image", candidate);
			final String oldImageUid = oldImage.getUid();
			this.fileService.removeFile(oldImageUid);
		}
		return newImage.getPath();
	}

	public String addFileToCandidate(final String candidateUid, final InputStream inputStream, String filename) {
		Objects.requireNonNull(inputStream);
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(filename);
		logger.debug("Add file {} to candidate {}", filename, candidateUid);
		final Candidate candidate = candidateDao.findCandidateWithFiles(candidateUid);
		final FileDto fileDto = fileService.uploadFile(inputStream, filename, false);
		final String uid = fileDto.getUid();
		final File file = fileDao.findFileByUid(uid);
		candidate.addFile(file);
		return fileDto.getPath();
	}

	public List<Origin> findAllOrigins() {
		logger.debug("Find all candidate origins");
		return this.candidateDao.findAllOrigins();
	}

	public List<CandidateDto> findByJobOffer(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(page);
		logger.debug("Find candidates by jobOffer {} ", jobOfferUid);
		final List<Candidate> candidates = this.candidateDao.findCandidates(jobOfferUid, page);
		return candidateMapper.mapList(candidates);
	}

	public CandidateDto findByUid(final String uid) {
		Objects.requireNonNull(uid);
		logger.debug("Find candidate by uid {}", uid);
		final Candidate candidates = this.candidateDao.findByUid(uid);
		return candidateMapper.map(candidates);
	}

	public List<File> findCandidatesFiles(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		logger.debug("Find files by candidate {}", candidateUid);
		return this.candidateDao.findCandidatesFiles(candidateUid, page);
	}

	public void addCandidate(final CandidateDto candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Add candidate {}", candidate);
		final Candidate entity = new Candidate();
		candidateMerger.merge(entity, candidate);
		this.candidateDao.insert(entity);
	}

	public void removeFileFromCandidate(final String candidateUid, final String fileUid) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(fileUid);
		logger.debug("Remove file {} from candidate {}", fileUid, candidateUid);
		final Candidate candidate = this.candidateDao.findCandidateWithFiles(candidateUid);
		final File file = this.fileDao.findFileByUid(fileUid);
		candidate.removeFile(file);
		final String uid = file.getUid();
		this.fileService.removeFile(uid);
	}

	public void setCandidateDao(final CandidateDao candidateDao) {
		Objects.requireNonNull(candidateDao);
		this.candidateDao = candidateDao;
	}

	public void updateCandidate(final CandidateDto candidate) {
		Objects.requireNonNull(candidate);
		logger.debug("Update candidate", candidate);
		final String uid = candidate.getUid();
		final Candidate entity = this.candidateDao.findByUid(uid);
		candidateMerger.merge(entity, candidate);
	}

	public SearchResult<CandidateDto> search(final String searchText, final Page page) {
		return search(searchText, page, null, null);
	}

	public SearchResult<CandidateDto> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		Objects.requireNonNull(searchText);
		Objects.requireNonNull(page);
		logger.debug("Search {}, offset {} with limit of {}", searchText, page.getOffset(), page.getLimit());
		final SearchResult<Candidate> entities = this.candidateDao.search(searchText, page, sortOrder, searchFacets);
		final List<Candidate> resultData = entities.getResultData();
		final List<CandidateDto> dtoList = candidateMapper.mapList(resultData);
		final int count = entities.getCount();
		final Map<String, List<Facet>> allFacets = entities.getAllFacets();
		return new SearchResult<CandidateDto>(dtoList, count, allFacets);
	}
}