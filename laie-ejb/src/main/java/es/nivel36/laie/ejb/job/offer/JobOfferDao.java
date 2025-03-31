package es.nivel36.laie.ejb.job.offer;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.user.User;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;

public class JobOfferDao extends AbstractDao {

	private @Inject SearchFacade searchFacade;

	public void addJobOfferEvent(final JobOfferEvent jobOfferEvent) {
		Objects.requireNonNull(jobOfferEvent);
		em.persist(jobOfferEvent);
	}

	public JobOfferState findFirstJobOfferState() {
		final String namedQuery = "JobOfferState.findFirst";
		return this.findByQuery(JobOfferState.class, namedQuery, null);
	}

	public JobOffer findJobOfferData(final long jobOfferId) {
		final String jpql = """
				SELECT j
				FROM JobOffer j
				LEFT JOIN FETCH j.recruiters
				LEFT JOIN FETCH j.client
				LEFT JOIN FETCH j.owner
				WHERE j.id=:jobOfferId
					""";
		final TypedQuery<JobOffer> query = this.em.createQuery(jpql, JobOffer.class);
		query.setParameter("jobOfferId", jobOfferId);
		return query.getSingleResult();
	}

	public List<JobOffer> findJobOffersByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT j
				FROM JobOffer j
				INNER JOIN j.jobSubmissions jc
				WHERE jc.candidate = :candidate
				""";
		final TypedQuery<JobOffer> query = this.em.createQuery(jpql, JobOffer.class);
		query.setParameter("candidate", candidate);
		this.paginate(page, query);
		return query.getResultList();
	}

	public List<JobOffer> findJobOffersByClient(final Client client, final Page page) {
		Objects.requireNonNull(client);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT j
				FROM JobOffer j
				LEFT JOIN FETCH j.owner
				LEFT JOIN FETCH j.client
				WHERE j.client = :client
					""";
		final TypedQuery<JobOffer> query = this.em.createQuery(jpql, JobOffer.class);
		query.setParameter("client", client);
		this.paginate(page, query);
		return query.getResultList();
	}

	public List<JobOffer> findJobOffersByOwnerOrRecruiter(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT j
				FROM JobOffer j
				LEFT JOIN FETCH j.client
				LEFT JOIN FETCH j.owner
				WHERE :user MEMBER OF j.recruiters
				OR j.owner = :user
					""";
		final TypedQuery<JobOffer> query = this.em.createQuery(jpql, JobOffer.class);
		query.setParameter("user", user);
		this.paginate(page, query);
		return query.getResultList();
	}

	public long countJobOffersByOwnerOrRecruiter(final User user) {
		Objects.requireNonNull(user);
		final String jpql = """
				SELECT COUNT(j)
				FROM JobOffer j
				WHERE :user MEMBER OF j.recruiters
				OR j.owner = :user
					""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("user", user);
		return query.getSingleResult();
	}

	public long countJobOfferEventsByJobOffer(final JobOffer jobOffer) {
		Objects.requireNonNull(jobOffer);
		final String jpql = """
				SELECT COUNT(j)
				FROM JobOfferEvent j
				WHERE j.jobOffer = :jobOffer
					""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("jobOffer", jobOffer);
		return query.getSingleResult();
	}

	public long countJobOffersByClient(final Client client) {
		Objects.requireNonNull(client);
		final String jpql = """
				SELECT COUNT(j)
				FROM JobOffer j
				WHERE j.client = :client
					""";
		final TypedQuery<Long> query = this.em.createQuery(jpql, Long.class);
		query.setParameter("client", client);
		return query.getSingleResult();
	}

	public List<JobOfferEvent> findJobOfferEventsByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT j
				FROM JobOfferEvent j
				LEFT JOIN FETCH j.jobOffer
				LEFT JOIN FETCH j.jobOffer.client
				LEFT JOIN FETCH j.user
				WHERE j.jobOffer = :jobOffer
						""";
		final TypedQuery<JobOfferEvent> query = this.em.createQuery(jpql, JobOfferEvent.class);
		query.setParameter("jobOffer", jobOffer);
		this.paginate(page, query);
		return query.getResultList();
	}

	public List<User> findRecruitersByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT u
				FROM User u
				WHERE :jobOffer MEMBER OF u.recruiterOfJobOffers
					""";
		final TypedQuery<User> query = this.em.createQuery(jpql, User.class);
		query.setParameter("jobOffer", jobOffer);
		this.paginate(page, query);
		return query.getResultList();
	}

	public List<JobOffer> findJobOffersToOpen() {
		final String jpql = """
				SELECT j 
				FROM JobOffer j
				WHERE j.openDate <=	CURRENT_DATE
				AND j.state=:state
					""";
		final TypedQuery<JobOffer> query = this.em.createQuery(jpql, JobOffer.class);
		query.setParameter("state", JobOfferState.CREATED);
		return query.getResultList();
	}

	public List<JobOffer> findJobOffersToClose() {
		final String jpql = """
				SELECT j
				FROM JobOffer j
				WHERE j.closeDate>CURRENT_DATE
					""";
		final TypedQuery<JobOffer> query = this.em.createQuery(jpql, JobOffer.class);
		return query.getResultList();
	}

	public SearchResult<JobOffer> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "_title", "client._name" };
		return searchFacade.search(JobOffer.class, page, sortField, searchFacets, searchText, searchFields);
	}
}
