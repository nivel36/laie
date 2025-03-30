package es.nivel36.laie.ejb.candidate;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class RatingDao extends AbstractDao {

	public List<Rating> findRatingsByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT r
				FROM Rating r
				LEFT JOIN FETCH r.user
				WHERE r.candidate = :candidate
				""";
		final TypedQuery<Rating> query = em.createQuery(jpql, Rating.class);
		query.setParameter("candidate", candidate);
		this.paginate(page, query);
		return query.getResultList();
	}

	public List<Rating> findRatingsByUser(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		final String jpql = """
				SELECT r
				FROM Rating r
				LEFT JOIN FETCH r.candidate
				WHERE r.user = :user
				""";
		final TypedQuery<Rating> query = em.createQuery(jpql, Rating.class);
		query.setParameter("user", user);
		this.paginate(page, query);
		return query.getResultList();
	}

	public Rating findRatingOfCandidateByUser(final Candidate candidate, final User user) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(candidate);
		try {
			final String jpql = """
					SELECT r
					FROM Rating r
					LEFT JOIN r.user u
					WHERE r.candidate = :candidate
					AND u=:user
					""";
			final TypedQuery<Rating> query = em.createQuery(jpql, Rating.class);
			query.setParameter("user", user);
			query.setParameter("candidate", candidate);
			return query.getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	public Rating findAllData(final long ratingId) {
		final String jpql = """
				SELECT r
				From Rating r
				LEFT JOIN FETCH	r.candidate
				LEFT JOIN FETCH	r.user
				WHERE r.id = :ratingId
				""";
		final TypedQuery<Rating> query = em.createQuery(jpql, Rating.class);
		query.setParameter("ratingId", ratingId);
		return query.getSingleResult();
	}
}
