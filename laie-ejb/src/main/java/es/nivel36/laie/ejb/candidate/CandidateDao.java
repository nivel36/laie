package es.nivel36.laie.ejb.candidate;

import static es.nivel36.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.core.model.AbstractDao;
import es.nivel36.core.model.Page;
import es.nivel36.core.model.Repository;
import es.nivel36.core.model.search.SearchFacets;
import es.nivel36.core.model.search.SearchResult;
import es.nivel36.core.model.search.SortField;
import es.nivel36.core.util.Parameters;

@Repository
public class CandidateDao extends AbstractDao {

	public void insert(final Candidate candidate) {
		Objects.requireNonNull(candidate);
		this.setUid(Candidate.class, candidate);
		this.em.persist(candidate);
	}

	public boolean checkDuplicateEmail(final String email) {
		Objects.requireNonNull(email);
		final String namedQuery = "Candidate.checkDuplicateEmail";
		final Parameters parameters = map("email", email);
		return this.findByQuery(Boolean.class, namedQuery, parameters);
	}

	public Candidate findByUid(final String uid) {
		Objects.requireNonNull(uid);
		final String namedQuery = "Candidate.findByUid";
		final Parameters parameters = map("uid", uid);
		return this.findByQuery(Candidate.class, namedQuery, parameters);
	}

	public List<Candidate> findCandidates(final String jobOfferUid, final Page page) {
		Objects.requireNonNull(jobOfferUid);
		Objects.requireNonNull(page);
		final String namedQuery = "Candidate.findByJobOffer";
		final Parameters parameters = map("jobOfferUid", jobOfferUid);
		return this.findByQuery(Candidate.class, namedQuery, parameters, page);
	}

	public List<String> findCandidatesFiles(final String candidateUid, final Page page) {
		Objects.requireNonNull(candidateUid);
		Objects.requireNonNull(page);
		final String namedQuery = "Candidate.findFiles";
		final Parameters parameters = map("uid", candidateUid);
		return this.findByQuery(String.class, namedQuery, parameters, page);
	}

	public Candidate findCandidateWithFiles(final String candidateUid) {
		Objects.requireNonNull(candidateUid);
		final String namedQuery = "Candidate.findCandidateWithFiles";
		final Parameters parameters = map("uid", candidateUid);
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