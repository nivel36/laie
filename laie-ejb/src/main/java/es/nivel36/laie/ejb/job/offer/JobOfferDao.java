package es.nivel36.laie.ejb.job.offer;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;

import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.search.SearchFacets;
import es.nivel36.laie.ejb.core.model.search.SearchResult;
import es.nivel36.laie.ejb.core.model.search.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.user.User;

public class JobOfferDao extends AbstractDao {

	@Inject
	private SearchFacade searchFacade;
	
	public void addJobOfferEvent(JobOfferEvent jobOfferEvent) {
		Objects.requireNonNull(jobOfferEvent);
		em.persist(jobOfferEvent);
	}

	public JobOfferState findFirstJobOfferState() {
		final String namedQuery = "JobOfferState.findFirst";
		return this.findByQuery(JobOfferState.class, namedQuery, null);
	}

	public List<JobOffer> findJobOffersByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findByCandidate";
		final Parameters parameters = map("candidate", candidate);
		return this.findByQuery(JobOffer.class, namedQuery, parameters, page);
	}

	public List<JobOffer> findJobOffersByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findByClient";
		final Parameters parameters = map("client", client);
		return this.findByQuery(JobOffer.class, namedQuery, parameters, page);
	}

	public List<JobOffer> findJobOffersByOwnerOrRecruiter(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findJobOffersByOwnerOrRecruiter";
		final Parameters parameters = map("user", user);
		return this.findByQuery(JobOffer.class, namedQuery, parameters, page);
	}

	public long countJobOfferEventsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String namedQuery = "JobOffer.countJobOfferEventsByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(Long.class, namedQuery, parameters).longValue();
	}
	
	public List<JobOfferEvent> findJobOfferEventsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findJobOfferEventsByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(JobOfferEvent.class, namedQuery, parameters, page);
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page, SortField sortOrder,
			final SearchFacets searchFacets) {
		final String[] searchFields = new String[] { "_title", "client._name" };
		return searchFacade.search(JobOffer.class, page, sortOrder, searchFacets, searchText, searchFields);
	}
}
