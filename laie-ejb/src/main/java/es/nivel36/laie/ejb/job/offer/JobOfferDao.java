package es.nivel36.laie.ejb.job.offer;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.candidate.Candidate;
import es.nivel36.laie.ejb.client.Client;
import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.user.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

/**
 * This DAO class is responsible for managing <tt>JobOffer</tt> entities and
 * their related data. It provides persistence and query operations using JPA,
 * including job offer events and associations with users, clients, and
 * candidates. It also supports full-text search through the
 * <tt>SearchFacade</tt>.
 */
@ApplicationScoped
public class JobOfferDao extends AbstractDao {

	/**
	 * Retrieves a <tt>JobOffer</tt> by ID, including its recruiters, client, and
	 * owner.
	 *
	 * @param jobOfferId the ID of the job offer to retrieve.
	 * @return the <tt>JobOffer</tt> with its associated data.
	 */
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

	/**
	 * Finds job offers in which a given candidate has submitted applications.
	 *
	 * @param candidate the <tt>Candidate</tt> to search for. Cannot be null.
	 * @param page      the <tt>Page</tt> object to control pagination. Cannot be
	 *                  null.
	 * @return a list of <tt>JobOffer</tt> instances associated with the candidate.
	 * @throws NullPointerException if any parameter is null.
	 */
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

	/**
	 * Finds job offers associated with a specific client.
	 *
	 * @param client the <tt>Client</tt> whose job offers to retrieve. Cannot be
	 *               null.
	 * @param page   the <tt>Page</tt> object to control pagination. Cannot be null.
	 * @return a list of <tt>JobOffer</tt> instances owned by the client.
	 * @throws NullPointerException if any parameter is null.
	 */
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

	/**
	 * Finds job offers in which a user is either the owner or a recruiter.
	 *
	 * @param user the <tt>User</tt> to search job offers for. Cannot be null.
	 * @param page the <tt>Page</tt> object to control pagination. Cannot be null.
	 * @return a list of <tt>JobOffer</tt> instances related to the user.
	 * @throws NullPointerException if any parameter is null.
	 */
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

	/**
	 * Counts the number of job offers in which a user is either the owner or a
	 * recruiter.
	 *
	 * @param user the <tt>User</tt> to count job offers for. Cannot be null.
	 * @return the number of job offers related to the user.
	 * @throws NullPointerException if <tt>user</tt> is null.
	 */
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

	/**
	 * Counts the number of events associated with a specific job offer.
	 *
	 * @param jobOffer the <tt>JobOffer</tt> whose events to count. Cannot be null.
	 * @return the number of events linked to the job offer.
	 * @throws NullPointerException if <tt>jobOffer</tt> is null.
	 */
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

	/**
	 * Counts the number of job offers associated with a specific client.
	 *
	 * @param client the <tt>Client</tt> whose job offers to count. Cannot be null.
	 * @return the number of job offers owned by the client.
	 * @throws NullPointerException if <tt>client</tt> is null.
	 */
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

	/**
	 * Retrieves all events linked to a specific job offer, including related user
	 * and client data.
	 *
	 * @param jobOffer the <tt>JobOffer</tt> whose events to retrieve. Cannot be
	 *                 null.
	 * @param page     the <tt>Page</tt> object to control pagination. Cannot be
	 *                 null.
	 * @return a list of <tt>JobOfferEvent</tt> instances related to the job offer.
	 * @throws NullPointerException if any parameter is null.
	 */
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

	/**
	 * Retrieves all recruiters associated with a given job offer.
	 *
	 * @param jobOffer the <tt>JobOffer</tt> to retrieve recruiters for. Cannot be
	 *                 null.
	 * @param page     the <tt>Page</tt> object to control pagination. Cannot be
	 *                 null.
	 * @return a list of <tt>User</tt> instances who are recruiters for the job
	 *         offer.
	 * @throws NullPointerException if any parameter is null.
	 */
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

	/**
	 * Retrieves job offers that are scheduled to be opened today or earlier and are
	 * still in <tt>CREATED</tt> state.
	 *
	 * @return a list of <tt>JobOffer</tt> instances ready to be opened.
	 */
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

	/**
	 * Retrieves job offers that are past their close date and thus are candidates
	 * for closure.
	 *
	 * @return a list of <tt>JobOffer</tt> instances ready to be closed.
	 */
	public List<JobOffer> findJobOffersToClose() {
		final String jpql = """
				SELECT j
				FROM JobOffer j
				WHERE j.closeDate>CURRENT_DATE
					""";
		final TypedQuery<JobOffer> query = this.em.createQuery(jpql, JobOffer.class);
		return query.getResultList();
	}

	/**
	 * Performs a full-text search on job offers using a text query, pagination,
	 * sorting, and facets.
	 *
	 * @param searchText   the text to search for.
	 * @param page         the <tt>Page</tt> object to control pagination.
	 * @param sortField    the <tt>SortField</tt> to sort the results.
	 * @param searchFacets an array of facet filters to apply to the search.
	 * @return the <tt>SearchResult</tt> containing matching job offers.
	 */
	public SearchResult<JobOffer> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "_title", "client._name" };
		return searchFacade.search(JobOffer.class, page, sortField, searchFacets, searchText, searchFields);
	}
}
