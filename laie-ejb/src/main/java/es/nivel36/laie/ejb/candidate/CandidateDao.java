package es.nivel36.laie.ejb.candidate;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.file.File;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.job.offer.JobOffer;

@Repository
public class CandidateDao extends AbstractDao {

	public boolean checkDuplicateEmail(final String email) {
		Objects.requireNonNull(email);
		final String namedQuery = "Candidate.checkDuplicateEmail";
		final Parameters parameters = map("email", email);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public List<Candidate> findCandidates(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "Candidate.findByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(Candidate.class, namedQuery, parameters, page);
	}

	public List<File> findCandidatesFiles(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String namedQuery = "Candidate.findFiles";
		final Parameters parameters = map("candidateId", candidate);
		return this.findByQuery(File.class, namedQuery, parameters, page);
	}

	public Candidate findCandidateWithFiles(final Long candidateId) {
		Objects.requireNonNull(candidateId);
		final String namedQuery = "Candidate.findCandidateWithFiles";
		final Parameters parameters = map("candidateId", candidateId);
		return this.findByQuery(Candidate.class, namedQuery, parameters);
	}

	public List<Origin> findAllOrigins() {
		return this.findAll(Origin.class, Page.ALL_RESULTS);
	}

	public SearchResult<Candidate> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "_name", "_surname", "_jobProfile", "tags._label" };
		return search(Candidate.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
}