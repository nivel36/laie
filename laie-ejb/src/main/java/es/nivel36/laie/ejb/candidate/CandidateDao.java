package es.nivel36.laie.ejb.candidate;

import java.util.List;
import java.util.Objects;

import org.hibernate.search.engine.search.query.SearchResult;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.model.SearchFacade;
import es.nivel36.laie.ejb.core.model.SortField;
import es.nivel36.laie.ejb.job.offer.JobOffer;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class CandidateDao extends AbstractDao {

	private @Inject SearchFacade searchFacade;

	public boolean checkDuplicateEmail(final String email) {
		Objects.requireNonNull(email);
		return this.fieldExists(Candidate.class, "email", email);
	}

	public List<Candidate> findCandidatesByJobOffer(final JobOffer jobOffer, final Page page) {
		Objects.requireNonNull(jobOffer);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT c
				FROM Candidate c
				LEFT JOIN c.jobSubmissions jc
				WHERE jc.jobOffer=:jobOffer
				""";
		final TypedQuery<Candidate> query = this.em.createQuery(jpql, Candidate.class);
		query.setParameter("jobOffer", jobOffer);
		this.paginate(page, query);
		return query.getResultList();
	}

	public Candidate findAllData(final long candidateId) {
		final String jpql = """
				SELECT c
				FROM Candidate c
				LEFT JOIN FETCH c.tags
				LEFT JOIN FETCH c.owner
				WHERE c.id = :candidateId
				""";
		final TypedQuery<Candidate> query = this.em.createQuery(jpql, Candidate.class);
		query.setParameter("candidateId", candidateId);
		return query.getSingleResult();
	}

	public Candidate findCandidateByEmail(final String email) {
		Objects.requireNonNull(email);
		try {
			final String jpql = """
					SELECT c
					FROM Candidate c
					WHERE c.email=:email
					""";
			final TypedQuery<Candidate> query = this.em.createQuery(jpql, Candidate.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public List<Origin> findAllOrigins() {
		return this.findAll(Origin.class, Page.ALL_RESULTS);
	}

	public SearchResult<Candidate> search(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "_name", "_surname", "_jobProfile", "tags._label" };
		return searchFacade.search(Candidate.class, page, sortField, searchFacets, searchText, searchFields);
	}

	public SearchResult<Candidate> searchByName(final String searchText, final Page page, final SortField sortField,
			final String[] searchFacets) {
		final String[] searchFields = new String[] { "_name", "_surname", "_email" };
		return searchFacade.search(Candidate.class, page, sortField, searchFacets, searchText, searchFields);
	}
}