package es.nivel36.laie.ejb.candidate;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.Repository;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.job.offer.JobOffer;

@Repository
public class CandidateDao extends AbstractDao {
	
	@Inject
	private SearchFacade searchFacade;

	public boolean checkDuplicateEmail(final String email) {
		Objects.requireNonNull(email);
		return this.checkDuplicateField(Candidate.class, "email", email);
	}

	public List<Candidate> findCandidatesByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "Candidate.findByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(Candidate.class, namedQuery, parameters, page);
	}

	public List<Origin> findAllOrigins() {
		return this.findAll(Origin.class, Page.ALL_RESULTS);
	}

	public SearchResult<Candidate> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "_name", "_surname", "_jobProfile", "tags._label" };
		return searchFacade.search(Candidate.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
	
	public SearchResult<Candidate> searchByName(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "_name", "_surname", "_email" };
		return searchFacade.search(Candidate.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
}