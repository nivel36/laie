package es.nivel36.laie.ejb.job.offer;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.user.User;
import jakarta.inject.Inject;

public class JobOfferDao extends AbstractDao {

	private @Inject SearchFacade searchFacade;

	public void addJobOfferEvent(JobOfferEvent jobOfferEvent) {
		Objects.requireNonNull(jobOfferEvent);
		em.persist(jobOfferEvent);
	}

	public JobOfferState findFirstJobOfferState() {
		final String namedQuery = "JobOfferState.findFirst";
		return this.findByQuery(JobOfferState.class, namedQuery, null);
	}

	public JobOffer findJobOfferData(final Long id) {
		Objects.requireNonNull(id);
		final String namedQuery = "JobOffer.findJobOfferData";
		final Parameters parameters = map("id", id);
		return this.findByQuery(JobOffer.class, namedQuery, parameters);
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

	public long countJobOffersByOwnerOrRecruiter(final User user) {
		Objects.requireNonNull(user);
		final String namedQuery = "JobOffer.countJobOffersByOwnerOrRecruiter";
		final Parameters parameters = map("user", user);
		return this.findByQuery(Long.class, namedQuery, parameters).longValue();
	}

	public long countJobOfferEventsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String namedQuery = "JobOffer.countJobOfferEventsByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(Long.class, namedQuery, parameters).longValue();
	}

	public long countJobOffersByClient(final Client client) {
		Objects.requireNonNull(client);
		final String namedQuery = "JobOffer.countByClient";
		final Parameters parameters = map("client", client);
		return this.findByQuery(Long.class, namedQuery, parameters).longValue();
	}

	public List<JobOfferEvent> findJobOfferEventsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findJobOfferEventsByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(JobOfferEvent.class, namedQuery, parameters, page);
	}
	
	public List<User> findRecruitersByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String namedQuery = "JobOffer.findRecruitersByJobOffer";
		final Parameters parameters = map("jobOffer", jobOffer);
		return this.findByQuery(User.class, namedQuery, parameters, page);
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "_title", "client._name" };
		return searchFacade.search(JobOffer.class, page, sortField, searchFacets, searchText, searchFields);
	}

	public List<JobOfferProcess> findJobOfferProcess() {
		final String namedQuery = "JobOffer.findJobOfferProcess";
		return this.findByQuery(JobOfferProcess.class, namedQuery, null, Page.ALL_RESULTS);
	}
	
	public List<JobOffer> findJobOffersToOpen(){
		final String namedQuery = "JobOffer.findJobOffersToOpen";
		final Parameters parameters = map("state", JobOfferState.CREATED);
		return this.findByQuery(JobOffer.class, namedQuery, parameters, Page.ALL_RESULTS);
	}
	
	public List<JobOffer> findJobOffersToClose(){
		final String namedQuery = "JobOffer.findJobOffersToClose";
		return this.findByQuery(JobOffer.class, namedQuery, null, Page.ALL_RESULTS);
	}
}
