package es.nivel36.laie.ejb.candidate;

import static es.nivel36.laie.ejb.core.util.Parameters.map;

import java.util.List;
import java.util.Objects;

import es.nivel36.laie.ejb.core.model.AbstractDao;
import es.nivel36.laie.ejb.core.model.Page;
import es.nivel36.laie.ejb.core.util.Parameters;
import es.nivel36.laie.ejb.user.User;
import jakarta.persistence.NoResultException;

public class RatingDao extends AbstractDao {

	public List<Rating> findRatingsByCandidate(final Candidate candidate, final Page page) {
		Objects.requireNonNull(candidate);
		Objects.requireNonNull(page);
		final String namedQuery = "Rating.findByCandidate";
		final Parameters parameters = map("candidate", candidate);
		return this.findByQuery(Rating.class, namedQuery, parameters, page);
	}

	public List<Rating> findRatingsByUser(final User user, final Page page) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(page);
		final String namedQuery = "Rating.findByUser";
		final Parameters parameters = map("user", user);
		return this.findByQuery(Rating.class, namedQuery, parameters, page);
	}

	public Rating findRatingOfCandidateByUser(final Candidate candidate, final User user) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(candidate);
		try {
			final String namedQuery = "Rating.findRatingOfCandidateByUser";
			final Parameters parameters = map("user", user).and("candidate", candidate);
			return this.findByQuery(Rating.class, namedQuery, parameters);
		} catch (NoResultException e) {
			return null;
		}
	}

	public Rating findAllData(long ratingId) {
		Objects.requireNonNull(ratingId);
		final String namedQuery = "Rating.findAllData";
		final Parameters parameters = map("ratingId", ratingId);
		return this.findByQuery(Rating.class, namedQuery, parameters);
	}
}
